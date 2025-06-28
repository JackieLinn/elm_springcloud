package ynu.jackielinn.food.service.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ynu.jackielinn.common.entity.RestBean;

import static java.lang.Thread.sleep;

@Component
public class BusinessFeignClientFallback implements BusinessFeignClient {
    private static final Logger logger = LoggerFactory.getLogger(BusinessFeignClientFallback.class);

    @Override
    public RestBean<Boolean> checkUserOwnsBusiness(Long userId, Long businessId) {
        return RestBean.failure(503, "服务降级：无法校验用户-商家归属，userId=" + userId + ", businessId=" + businessId);
    }
} 