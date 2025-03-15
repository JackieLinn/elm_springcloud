package ynu.jackielinn.test2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.common.entity.RestBean;

@RestController
@RequestMapping("/api/test2")
public class Test2Controller {

    @GetMapping("/test2")
    public RestBean<String> test2() {
        System.out.println("test2被调用");
        return RestBean.success("test2被调用");
    }
}
