package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.orders.dto.response.BusinessVO;

@FeignClient(value = "business-service", path = "/api/business", fallback = BusinessFeignClientFallback.class)
public interface BusinessFeignClient {

    @GetMapping("/get-business-by-businessId-remote")
    BusinessVO listBusinessByBusinessIdRemote(@RequestParam Long businessId);
}
