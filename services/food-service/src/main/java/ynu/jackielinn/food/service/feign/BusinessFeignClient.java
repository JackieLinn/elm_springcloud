package ynu.jackielinn.food.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.common.entity.RestBean;

@FeignClient(value = "business-service", path = "/api/business", fallback = BusinessFeignClientFallback.class)
public interface BusinessFeignClient {
    @GetMapping("/user-business/check")
    RestBean<Boolean> checkUserOwnsBusiness(@RequestParam("userId") Long userId, @RequestParam("businessId") Long businessId);
} 