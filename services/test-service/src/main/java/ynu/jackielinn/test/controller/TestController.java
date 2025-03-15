package ynu.jackielinn.test.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.test.service.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public RestBean<String> test() {
        return RestBean.success(testService.test());
    }
}
