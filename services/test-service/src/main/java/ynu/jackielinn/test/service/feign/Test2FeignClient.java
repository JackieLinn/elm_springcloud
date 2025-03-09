package ynu.jackielinn.test.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "test2-service", path = "/api/test2")
public interface Test2FeignClient {

    @GetMapping("/test2")
    String test2();
}
