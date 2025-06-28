package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.orders.dto.response.Business;
import ynu.jackielinn.orders.dto.response.BusinessVO;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "business-service", path = "/api/business", fallback = BusinessFeignClientFallback.class)
public interface BusinessFeignClient {

    @GetMapping("/get-business-by-businessId-remote")
    BusinessVO listBusinessByBusinessIdRemote(@RequestParam Long businessId);

    @GetMapping("/get-business-info")
    Map<Long, Business> getBusinessInfo(@RequestParam Set<Long> businessIds);

    @GetMapping("/user-business/check")
    Boolean checkUserOwnsBusiness(@RequestParam("userId") Long userId, @RequestParam("businessId") Long businessId);
}
