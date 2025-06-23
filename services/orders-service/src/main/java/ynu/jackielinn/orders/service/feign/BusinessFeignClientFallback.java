package ynu.jackielinn.orders.service.feign;

import org.springframework.stereotype.Component;
import ynu.jackielinn.orders.dto.response.BusinessVO;

@Component
public class BusinessFeignClientFallback implements BusinessFeignClient {

    @Override
    public BusinessVO listBusinessByBusinessIdRemote(Long businessId) {
        BusinessVO fallbackVO = new BusinessVO();
        fallbackVO.setBusinessId(businessId);
        fallbackVO.setBusinessName("服务降级");
        fallbackVO.setBusinessExplain("服务降级");
        fallbackVO.setBusinessImg("服务降级");
        fallbackVO.setStartPrice(0.0);
        fallbackVO.setDeliveryPrice(0.0);
        return fallbackVO;
    }
}
