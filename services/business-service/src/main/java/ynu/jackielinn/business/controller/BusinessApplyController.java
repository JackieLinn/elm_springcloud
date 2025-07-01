package ynu.jackielinn.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.business.dto.request.BusinessApplyRO;
import ynu.jackielinn.business.entity.BusinessApply;
import ynu.jackielinn.business.service.BusinessApplyService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.List;

@RestController
@RequestMapping("/api/business")
@Tag(name = "商家入驻申请相关接口", description = "与商家入驻申请相关的操作接口")
public class BusinessApplyController {
    @Resource
    private BusinessApplyService businessApplyService;

    /**
     * 商家端：提交入驻申请（使用userName）
     * 新的接口，解决用户未登录时无法获取userId的问题
     */
    @Operation(summary = "提交商家入驻申请（使用用户名）", description = "通过用户名提交商家入驻申请，无需用户登录")
    @PostMapping("/merchant/apply")
    public RestBean<Boolean> applyForMerchant(@RequestBody BusinessApplyRO applyRO) {
        boolean result = businessApplyService.applyForMerchantByUserName(applyRO);
        return result ? RestBean.success(true) : RestBean.failure(400, "申请失败：用户不存在或已提交过申请");
    }

    /**
     * 商家端：提交入驻申请（使用applicantId，保持向后兼容）
     * 原有的接口，保留以支持已登录用户使用
     */
    @Operation(summary = "提交商家入驻申请（使用用户ID）", description = "通过用户ID提交商家入驻申请，需要用户已登录")
    @PostMapping("/merchant/apply-by-id")
    public RestBean<Boolean> applyForMerchantById(@RequestBody BusinessApply apply) {
        boolean result = businessApplyService.applyForMerchant(apply);
        return result ? RestBean.success(true) : RestBean.failure(400, "已提交过申请或有待审核申请");
    }

    /**
     * 商家端：查询入驻申请状态
     */
    @Operation(summary = "查询入驻申请状态", description = "查询指定用户的所有入驻申请状态")
    @GetMapping("/merchant/status")
    public RestBean<List<BusinessApply>> getApplyStatus(@RequestParam Long userId) {
        List<BusinessApply> applies = businessApplyService.getAppliesByUserId(userId);
        return RestBean.success(applies);
    }

    /**
     * 管理员端：分页查询入驻申请列表
     */
    @Operation(summary = "管理员分页查询入驻申请列表", description = "管理员分页查询入驻申请列表，支持按状态筛选")
    @GetMapping("/admin/apply/list")
    public RestBean<IPage<BusinessApply>> listApplies(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) Integer status) {
        Page<BusinessApply> page = new Page<>(pageNum, pageSize);
        IPage<BusinessApply> result = businessApplyService.listApplies(page, status);
        return RestBean.success(result);
    }

    /**
     * 管理员端：审核入驻申请
     */
    @Operation(summary = "管理员审核入驻申请", description = "管理员审核入驻申请，通过后自动升级用户角色为商家")
    @PostMapping("/admin/apply/review")
    public RestBean<Boolean> reviewApply(@RequestParam Long applyId, @RequestParam Integer result, @RequestParam String reviewReason) {
        boolean success = businessApplyService.reviewApply(applyId, result, reviewReason);
        return success ? RestBean.success(true) : RestBean.failure(400, "审核失败，申请不存在或已审核");
    }
} 