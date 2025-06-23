package ynu.jackielinn.orders.service.feign;

import org.springframework.stereotype.Component;
import ynu.jackielinn.orders.dto.response.Business;
import ynu.jackielinn.orders.dto.response.BusinessVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Map<Long, Business> getBusinessInfo(Set<Long> businessIds) {
        Map<Long, Business> fallbackMap = new HashMap<>();
        for (Long id : businessIds) {
            Business business = new Business();
            business.setBusinessId(id);
            business.setBusinessName("服务降级");
            business.setBusinessAddress("服务降级");
            business.setBusinessExplain("服务降级");
            business.setBusinessImg("服务降级");
            business.setOrderTypeId(0);
            business.setStartPrice(0.0);
            business.setDeliveryPrice(0.0);
            business.setRemarks("服务降级");
            fallbackMap.put(id, business);
        }
        return fallbackMap;
    }
}
