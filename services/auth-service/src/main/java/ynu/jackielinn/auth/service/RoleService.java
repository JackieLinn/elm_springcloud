package ynu.jackielinn.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.auth.entity.Role;

public interface RoleService extends IService<Role> {

    Role findRoleByRID(long rid);
}
