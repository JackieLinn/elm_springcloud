package ynu.jackielinn.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.business.entity.UserBusiness;
import ynu.jackielinn.business.mapper.UserBusinessMapper;
import ynu.jackielinn.business.service.UserBusinessService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBusinessServiceImpl extends ServiceImpl<UserBusinessMapper, UserBusiness> implements UserBusinessService {
    @Override
    public List<Long> getBusinessIdsByUserId(Long userId) {
        List<UserBusiness> list = this.list(new QueryWrapper<UserBusiness>().eq("userId", userId));
        return list.stream().map(UserBusiness::getBusinessId).collect(Collectors.toList());
    }

    @Override
    public boolean checkUserOwnsBusiness(Long userId, Long businessId) {
        return this.count(new QueryWrapper<UserBusiness>().eq("userId", userId).eq("businessId", businessId)) > 0;
    }
} 