package ynu.jackielinn.business.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 远程调用：根据用户名查询用户ID
     * @param userName 用户名
     * @return 用户ID，如果用户不存在则返回null
     */
    @GetMapping("/remote/get-user-id")
    RestBean<Long> getUserIdByUserName(@RequestParam("userName") String userName);
} 