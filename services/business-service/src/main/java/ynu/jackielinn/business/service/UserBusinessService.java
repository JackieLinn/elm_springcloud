package ynu.jackielinn.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.business.entity.UserBusiness;

import java.util.List;

public interface UserBusinessService extends IService<UserBusiness> {
    /**
     * 根据用户ID查询其拥有的所有商家ID
     */
    List<Long> getBusinessIdsByUserId(Long userId);

    /**
     * 校验用户是否拥有某个商家
     */
    boolean checkUserOwnsBusiness(Long userId, Long businessId);
} 