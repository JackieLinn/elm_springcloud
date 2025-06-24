package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.common.entity.RestBean;

@FeignClient(value = "auth-service", path = "/api/account", fallback = AccountFeignClientFallback.class)
public interface AccountFeignClient {

    @GetMapping("/pay")
    RestBean<Boolean> pay(@RequestParam Long userId, @RequestParam Double price, @RequestHeader("Authorization") String token);
}
