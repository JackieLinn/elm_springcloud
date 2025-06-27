package ynu.jackielinn.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import ynu.jackielinn.auth.dto.request.ConfirmResetRO;
import ynu.jackielinn.auth.dto.request.EmailRegisterRO;
import ynu.jackielinn.auth.dto.request.EmailResetRO;
import ynu.jackielinn.auth.dto.response.AccountVO;
import ynu.jackielinn.auth.entity.Account;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AccountService extends IService<Account>, UserDetailsService {

    Account findAccountByUsernameOrEmail(String text);

    String registerEmailVerifyCode(String type, String email, String ip);

    String registerEmailAccount(EmailRegisterRO ro);

    String resetConfirm(ConfirmResetRO ro);

    String resetEmailAccountPassword(EmailResetRO ro);

    AccountVO getAccountByUserId(Long userId);

    Boolean pay(Long userId, Double price);

    Boolean refund(Long userId, Double price);

    IPage<AccountVO> listAccounts(int pageNum, int pageSize, Long roleId);
}
