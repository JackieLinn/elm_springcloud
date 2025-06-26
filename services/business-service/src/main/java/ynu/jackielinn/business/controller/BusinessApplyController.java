package ynu.jackielinn.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.business.entity.BusinessApply;
import ynu.jackielinn.business.service.BusinessApplyService;
import ynu.jackielinn.common.entity.RestBean;

@RestController
@RequestMapping("/api/business")
public class BusinessApplyController {
    @Resource
    private BusinessApplyService businessApplyService;

    /**
     * 商家端：提交入驻申请
     */
    @PostMapping("/merchant/apply")
    public RestBean<Boolean> applyForMerchant(@RequestBody BusinessApply apply) {
        boolean result = businessApplyService.applyForMerchant(apply);
        return result ? RestBean.success(true) : RestBean.failure(400, "已提交过申请或有待审核申请");
    }

    /**
     * 商家端：查询入驻申请状态
     */
    @GetMapping("/merchant/status")
    public RestBean<BusinessApply> getApplyStatus(@RequestParam Long userId) {
        BusinessApply apply = businessApplyService.getApplyStatus(userId);
        return RestBean.success(apply);
    }

    /**
     * 管理员端：分页查询入驻申请列表
     */
    @GetMapping("/admin/apply/list")
    public RestBean<IPage<BusinessApply>> listApplies(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) Integer status) {
        Page<BusinessApply> page = new Page<>(pageNum, pageSize);
        IPage<BusinessApply> result = businessApplyService.listApplies(page, status);
        return RestBean.success(result);
    }

    /**
     * 管理员端：审核入驻申请
     */
    @PostMapping("/admin/apply/review")
    public RestBean<Boolean> reviewApply(@RequestParam Long applyId, @RequestParam Integer result, @RequestParam String reviewReason) {
        boolean success = businessApplyService.reviewApply(applyId, result, reviewReason);
        return success ? RestBean.success(true) : RestBean.failure(400, "审核失败，申请不存在或已审核");
    }
} 