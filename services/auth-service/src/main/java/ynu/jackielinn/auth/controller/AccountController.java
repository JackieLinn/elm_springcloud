package ynu.jackielinn.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.auth.dto.response.AccountVO;
import ynu.jackielinn.auth.entity.AccountRole;
import ynu.jackielinn.auth.service.AccountService;
import ynu.jackielinn.auth.service.AccountRoleService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@Tag(name = "用户相关接口", description = "与用户相关的操作接口")
public class AccountController {

    @Resource
    AccountService accountService;

    @Resource
    AccountRoleService accountRoleService;

    @Operation(summary = "通过用户编号获取用户信息", description = "通过用户编号获取用户信息")
    @GetMapping("/get-account-by-userId")
    public RestBean<AccountVO> getAccountByUserId(Long userId) {
        return RestBean.success(accountService.getAccountByUserId(userId));
    }

    @Operation(summary = "远程调用：支付操作", description = "远程调用：支付操作")
    @GetMapping("/pay")
    public RestBean<Boolean> pay(@RequestParam Long userId, @RequestParam Double price) {
        return RestBean.success(accountService.pay(userId, price));
    }

    @Operation(summary = "更新用户角色", description = "根据用户ID更新其角色ID")
    @PostMapping("/role/update")
    public RestBean<Boolean> updateUserRole(@RequestParam Long userId, @RequestParam Long roleId) {
        // 先查找用户角色记录
        AccountRole accountRole = accountRoleService.findRIDByUID(userId);
        if (accountRole == null) {
            return RestBean.failure(404, "用户角色记录不存在");
        }
        accountRole.setRoleId(roleId);
        boolean updated = accountRoleService.updateById(accountRole);
        return updated ? RestBean.success(true) : RestBean.failure(500, "更新失败");
    }

    @Operation(summary = "远程调用：退款操作", description = "远程调用：退款操作")
    @GetMapping("/refund")
    public RestBean<Boolean> refund(@RequestParam Long userId, @RequestParam Double price) {
        return RestBean.success(accountService.refund(userId, price));
    }

    @Operation(summary = "管理员分页查询所有用户", description = "支持按角色筛选")
    @GetMapping("/admin/list")
    public RestBean<com.baomidou.mybatisplus.core.metadata.IPage<AccountVO>> listAccounts(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) Long roleId) {
        return RestBean.success(accountService.listAccounts(pageNum, pageSize, roleId));
    }
}
