package ynu.jackielinn.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.auth.entity.Role;
import ynu.jackielinn.auth.mapper.RoleMapper;
import ynu.jackielinn.auth.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Role findRoleByRID(long rid) {
        return this.query()
                .eq("roleId", rid)
                .one();
    }
}
