package ynu.jackielinn.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.auth.dto.response.AccountVO;
import ynu.jackielinn.auth.service.AccountService;
import ynu.jackielinn.common.entity.RestBean;

@RestController
@RequestMapping("/api/account")
@Tag(name = "用户相关接口", description = "与用户相关的操作接口")
public class AccountController {

    @Resource
    AccountService accountService;

    @Operation(summary = "通过用户编号获取用户信息", description = "通过用户编号获取用户信息")
    @GetMapping("/get-account-by-userId")
    public RestBean<AccountVO> getAccountByUserId(Long userId) {
        return RestBean.success(accountService.getAccountByUserId(userId));
    }

    @Operation(summary = "远程调用：支付操作", description = "远程调用：支付操作")
    @PostMapping("/pay")
    public Boolean pay(Long userId, Double price) {
        return accountService.pay(userId, price);
    }
}
