package ynu.jackielinn.business.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.common.entity.RestBean;

@FeignClient(name = "auth-service", path = "/api/account", fallback = AccountFeignClientFallback.class)
public interface AccountFeignClient {
    @PostMapping("/role/update")
    RestBean<Boolean> updateRole(
        @RequestParam("userId") Long userId,
        @RequestParam("roleId") Integer roleId,
        @RequestHeader("Authorization") String token
    );
} 