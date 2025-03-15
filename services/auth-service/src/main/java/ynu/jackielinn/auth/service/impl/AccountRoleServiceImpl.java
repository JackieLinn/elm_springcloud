package ynu.jackielinn.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.auth.entity.AccountRole;
import ynu.jackielinn.auth.mapper.AccountRoleMapper;
import ynu.jackielinn.auth.service.AccountRoleService;

@Service
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleMapper, AccountRole>
        implements AccountRoleService {
    @Override
    public AccountRole findRIDByUID(long uid) {
        return this.query()
                .eq("userId", uid)
                .one();
    }

    @Override
    public boolean registerAccountRole(long uid) {
        AccountRole accountRole = new AccountRole(null, uid, 2L);
        return this.save(accountRole);
    }
}
