package ynu.jackielinn.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ynu.jackielinn.auth.dto.request.ConfirmResetRO;
import ynu.jackielinn.auth.dto.request.EmailRegisterRO;
import ynu.jackielinn.auth.dto.request.EmailResetRO;
import ynu.jackielinn.auth.dto.response.AccountVO;
import ynu.jackielinn.auth.entity.Account;
import ynu.jackielinn.auth.entity.Role;
import ynu.jackielinn.auth.mapper.AccountMapper;
import ynu.jackielinn.auth.service.AccountRoleService;
import ynu.jackielinn.auth.service.AccountService;
import ynu.jackielinn.auth.service.RoleService;
import ynu.jackielinn.auth.utils.AuthConvertUtils;
import ynu.jackielinn.auth.utils.FlowUtils;
import ynu.jackielinn.common.utils.Const;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    FlowUtils flowUtils;

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder encoder;

    @Resource
    AccountRoleService accountRoleService;

    @Resource
    RoleService roleService;

    /**
     * 根据用户名或邮箱查找账户
     *
     * @param text 用户名或邮箱
     * @return 找到的账户，如果没有找到则返回 null
     */
    public Account findAccountByUsernameOrEmail(String text) {
        return this.query()
                .eq("username", text).or()
                .eq("email", text)
                .one();
    }

    /**
     * 注册或重置密码时，生成并发送电子邮件验证码
     *
     * @param type 操作类型，"register" 表示注册，"reset" 表示重置密码
     * @param email 要发送验证码的电子邮件地址
     * @param ip 发送验证码请求的 IP 地址，用于限制请求频率
     * @return 如果操作成功，返回 null；如果请求过于频繁，返回错误信息
     */
    public String registerEmailVerifyCode(String type, String email, String ip) {
        synchronized (ip.intern()) {
            if (!this.verifyLimit(ip)) {
                return "请求频繁，请稍后再试";
            }
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of("type", type, "email", email, "code", code);
            amqpTemplate.convertAndSend("ELMMail", data);
            stringRedisTemplate.opsForValue()
                    .set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        }
    }

    /**
     * 邮件验证码注册账号操作，需要检查验证码是否正确以及邮箱、用户名是否存在重名
     *
     * @param ro 注册基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String registerEmailAccount(EmailRegisterRO ro) {
        String email = ro.getEmail();
        String username = ro.getUsername();
        String key = Const.VERIFY_EMAIL_DATA + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) return "请先获取验证码";
        if (!code.equals(ro.getCode())) return "验证码错误，请重新输入";
        if (this.existsAccountByEmail(email)) return "此电子邮件已被其他用户注册";
        if (this.existsAccountByUsername(username)) return "此用户名已被其他人注册，请更换一个新的用户名";
        String password = encoder.encode(ro.getPassword());
        Account account = new Account(null, username, password, 1, email,
                "/img/userImg/yhtx01.png", 1000.00, 1);
        if (this.save(account)) {
            if (accountRoleService.registerAccountRole(account.getUserId())) {
                stringRedisTemplate.delete(key);
                return null;
            } else {
                return "内部错误，请联系管理员";
            }
        } else {
            return "内部错误，请联系管理员";
        }
    }

    /**
     * 重置密码确认操作，验证验证码是否正确
     *
     * @param ro 验证基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String resetConfirm(ConfirmResetRO ro) {
        String email = ro.getEmail();
        String code = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + email);
        if (code == null) return "请先获取验证码";
        if (!code.equals(ro.getCode())) return "验证码错误，请重新输入";
        return null;
    }

    /**
     * 邮件验证码重置密码操作，需要检查验证码是否正确
     *
     * @param ro 重置基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String resetEmailAccountPassword(EmailResetRO ro) {
        String verify = resetConfirm(new ConfirmResetRO(ro.getEmail(), ro.getCode()));
        if (verify != null) return verify;
        String email = ro.getEmail();
        String password = encoder.encode(ro.getPassword());
        boolean update = this.update().eq("email", email).set("password", password).update();
        if (update) {
            stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email);
        }
        return update ? null : "更新失败，请联系管理员";
    }

    /**
     * 根据用户ID获取用户账户信息
     *
     * @param userId 用户ID
     * @return 用户账户信息，如果用户ID为空或用户不存在，则返回null
     * @throws IllegalArgumentException 如果用户ID为空
     */
    public AccountVO getAccountByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Account account = baseMapper.selectById(userId);
        if (account == null) return null;
        return AuthConvertUtils.account2VO(account);
    }

    public Boolean pay(Long userId, Double price) {
        Account account = baseMapper.selectById(userId);
        if (account == null) return false;
        if (account.getBalance() < price) return false;
        Double newBalance = account.getBalance() - price;
        account.setBalance(newBalance);
        int result = baseMapper.updateById(account);
        return result > 0;
    }

    @Override
    public Boolean refund(Long userId, Double price) {
        Account account = baseMapper.selectById(userId);
        if (account == null) return false;
        account.setBalance(account.getBalance() + price);
        int result = baseMapper.updateById(account);
        return result > 0;
    }

    /**
     * 从数据库中通过用户名或邮箱查找用户详细信息
     *
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 如果用户未找到则抛出此异常
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByUsernameOrEmail(username);
        if (account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        Role role = roleService.findRoleByRID(accountRoleService.findRIDByUID(account.getUserId()).getRoleId());
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles(role.getRoleName())
                .build();
    }

    /**
     * 验证指定 IP 在一定时间内是否已经达到了发送邮件的次数限制
     *
     * @param ip 要验证的 IP 地址
     * @return 如果未达到限制，返回 true；如果已经达到限制，返回 false
     */
    private boolean verifyLimit(String ip) {
        String key = Const.VERIFY_EMAIL_LIMIT + ip;
        return flowUtils.limitOnceCheck(key, 60);
    }

    /**
     * 查询指定邮箱的用户是否已经存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    private boolean existsAccountByEmail(String email) {
        return this.baseMapper.exists(Wrappers.<Account>query().eq("email", email));
    }

    /**
     * 查询指定用户名的用户是否已经存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    private boolean existsAccountByUsername(String username) {
        return this.baseMapper.exists(Wrappers.<Account>query().eq("username", username));
    }

    @Override
    public com.baomidou.mybatisplus.core.metadata.IPage<AccountVO> listAccounts(int pageNum, int pageSize, Long roleId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Account> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        if (roleId != null) {
            List<Long> userIds = accountRoleService.list(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ynu.jackielinn.auth.entity.AccountRole>().eq("roleId", roleId)
            ).stream().map(ynu.jackielinn.auth.entity.AccountRole::getUserId).toList();
            if (userIds.isEmpty()) return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize);
            wrapper.in("userId", userIds);
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Account> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize);
        com.baomidou.mybatisplus.core.metadata.IPage<Account> result = this.page(page, wrapper);
        // 转换为 AccountVO 分页对象
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<AccountVO> voPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize, result.getTotal());
        voPage.setRecords(result.getRecords().stream().map(AuthConvertUtils::account2VO).toList());
        return voPage;
    }

    /**
     * 根据用户名查询用户ID
     * 此方法通过用户名从数据库中查询对应的用户ID
     * 如果找到对应的用户，则返回其用户ID；如果没有找到，则返回null
     *
     * @param userName 用户名，用于查询数据库中的用户记录
     * @return 用户ID，如果找到对应的用户，则返回用户ID；否则返回null
     */
    @Override
    public Long getUserIdByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return null;
        }
        Account account = this.query().eq("userName", userName.trim()).one();
        return account != null ? account.getUserId() : null;
    }
}
