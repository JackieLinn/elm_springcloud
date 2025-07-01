package ynu.jackielinn.business.service.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ynu.jackielinn.common.entity.RestBean;

@Component
public class AccountFeignClientFallback implements AccountFeignClient {
    private static final Logger logger = LoggerFactory.getLogger(AccountFeignClientFallback.class);

    @Override
    public RestBean<Boolean> updateRole(Long userId, Integer roleId, String token) {
        logger.warn("服务降级：无法调用角色升级服务，用户ID：{}，目标角色ID：{}", userId, roleId);
        return RestBean.failure(503, "角色升级服务当前不可用");
    }

    @Override
    public RestBean<Long> getUserIdByUserName(String userName) {
        // 降级处理：记录日志，返回null表示用户不存在
        logger.warn("服务降级：无法调用用户查询服务，用户名：{}", userName);
        return RestBean.failure(503, "用户查询服务当前不可用");
    }
} 