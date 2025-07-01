package ynu.jackielinn.orders.service.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ynu.jackielinn.orders.dto.response.Business;
import ynu.jackielinn.orders.dto.response.BusinessVO;
import ynu.jackielinn.common.entity.RestBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class BusinessFeignClientFallback implements BusinessFeignClient {
    private static final Logger logger = LoggerFactory.getLogger(BusinessFeignClientFallback.class);

    @Override
    public BusinessVO listBusinessByBusinessIdRemote(Long businessId) {
        logger.warn("服务降级：无法获取商家信息，businessId={}", businessId);
        return null;
    }

    @Override
    public Map<Long, Business> getBusinessInfo(Set<Long> businessIds) {
        logger.warn("服务降级：无法批量获取商家信息，businessIds={}", businessIds);
        return Collections.emptyMap();
    }

    @Override
    public RestBean<Boolean> checkUserOwnsBusiness(Long userId, Long businessId) {
        logger.warn("服务降级：无法校验用户-商家归属，userId={}, businessId={}", userId, businessId);
        return RestBean.failure(503, "服务降级，无法校验用户-商家归属");
    }
}
