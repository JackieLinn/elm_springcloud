package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "auth-service", path = "/api/account", fallback = AccountFeignClientFallback.class)
public interface AccountFeignClient {

    @PostMapping("/pay")
    Boolean pay(Long userId, Double price);
}
