package ynu.jackielinn.test.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.jackielinn.test.service.TestService;
import ynu.jackielinn.test.service.feign.Test2FeignClient;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private Test2FeignClient test2FeignClient;

    @Override
    public String test() {
        return "test " + test2FeignClient.test2();
    }
}
