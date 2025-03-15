package ynu.jackielinn.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.auth.entity.AccountRole;

public interface AccountRoleService extends IService<AccountRole> {

    AccountRole findRIDByUID(long uid);

    boolean registerAccountRole(long uid);
}
