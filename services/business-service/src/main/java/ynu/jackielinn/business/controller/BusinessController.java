package ynu.jackielinn.business.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.entity.Business;
import ynu.jackielinn.business.entity.BusinessEsDoc;
import ynu.jackielinn.business.service.BusinessService;
import ynu.jackielinn.business.service.UserBusinessService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/business")
@Tag(name = "商家相关接口", description = "与商家相关的操作接口")
public class BusinessController {

    @Resource
    BusinessService businessService;

    @Resource
    UserBusinessService userBusinessService;

    @Operation(summary = "获取所有点餐分类", description = "获取所有点餐分类")
    @GetMapping("/get-all-categories")
    public RestBean<Map<Integer, String>> getAllCategories() {
        List<Integer> categories = businessService.getAllCategories();
        Map<Integer, String> categoryMap = getCategoryMap();
        Map<Integer, String> result = new HashMap<>();
        for (Integer category : categories) {
            if (categoryMap.containsKey(category)) {
                result.put(category, categoryMap.get(category));
            }
        }
        return RestBean.success(result);
    }

    @Operation(summary = "获取推荐商家", description = "获取推荐商家")
    @GetMapping("/get-recommend-business")
    public RestBean<List<BusinessVO>> getRecommendBusiness() {
        return RestBean.success(businessService.getRecommendBusiness());
    }

    @Operation(summary = "根据点餐分类获取商家", description = "根据点餐分类获取商家")
    @GetMapping("/get-business-by-orderTypeId")
    public RestBean<List<BusinessVO>> listBusinessByOrderTypeId(@RequestParam Integer orderTypeId) {
        return RestBean.success(businessService.listBusinessByOrderTypeId(orderTypeId));
    }

    @Operation(summary = "通过商家编号获取商家信息", description = "通过商家编号获取商家信息")
    @GetMapping("/get-business-by-businessId")
    public RestBean<BusinessVO> listBusinessByBusinessId(@RequestParam Long businessId) {
        return RestBean.success(businessService.listBusinessByBusinessId(businessId));
    }

    @Operation(summary = "获取商家配送费", description = "获取商家配送费")
    @GetMapping("/get-delivery-price")
    public RestBean<Double> getDeliveryPriceByBusinessId(@RequestParam Long businessId) {
        return RestBean.success(businessService.getDeliveryPriceByBusinessId(businessId));
    }

    /**
     * 获取点餐分流ID和类型名称的映射
     *
     * @return 一个包含订单类型ID和类型名称的映射
     */
    private Map<Integer, String> getCategoryMap() {
        Map<Integer, String> categoryMap = new HashMap<>();
        categoryMap.put(1, "美食");
        categoryMap.put(2, "早餐");
        categoryMap.put(3, "跑腿代购");
        categoryMap.put(4, "汉堡披萨");
        categoryMap.put(5, "甜品饮品");
        categoryMap.put(6, "速食简餐");
        categoryMap.put(7, "地方小吃");
        categoryMap.put(8, "米粉面馆");
        categoryMap.put(9, "包子粥铺");
        categoryMap.put(10, "炸鸡炸串");
        return categoryMap;
    }

    @Operation(summary = "远程调用：通过商家编号获取商家信息", description = "远程调用：通过商家编号获取商家信息")
    @GetMapping("/get-business-by-businessId-remote")
    public BusinessVO listBusinessByBusinessIdRemote(@RequestParam Long businessId) {
        return businessService.listBusinessByBusinessId(businessId);
    }

    @Operation(summary = "远程调用：通过商家编号列表获取商家信息", description = "远程调用：通过商家编号列表获取商家信息")
    @GetMapping("/get-business-info")
    public Map<Long, Business> getBusinessInfo(@RequestParam Set<Long> businessIds) {
        return businessService.getBusinessInfo(businessIds);
    }

    @Operation(summary = "搜索商家", description = "搜索商家")
    @GetMapping("/search")
    public RestBean<List<BusinessEsDoc>> search(@RequestParam String keyword) {
        return RestBean.success(businessService.searchByName(keyword));
    }

    @Operation(summary = "根据用户ID获取其所有商家信息", description = "商家端：我的店铺列表")
    @GetMapping("/list-by-user")
    public RestBean<List<BusinessVO>> listBusinessByUserId(@RequestParam Long userId) {
        List<Long> businessIds = userBusinessService.getBusinessIdsByUserId(userId);
        return RestBean.success(businessIds.stream().map(businessService::listBusinessByBusinessId).toList());
    }

    @Operation(summary = "修改商家信息（带用户校验）", description = "商家端修改自己的店铺信息，需校验归属")
    @PostMapping("/update-info-by-user")
    public RestBean<Boolean> updateBusinessInfoByUser(@RequestParam Long userId, @RequestBody Business business) {
        boolean owns = userBusinessService.checkUserOwnsBusiness(userId, business.getBusinessId());
        if (!owns) return RestBean.failure(403, "无权修改该商家信息");
        return RestBean.success(businessService.updateBusinessInfo(business));
    }

    @Operation(summary = "远程调用：校验用户是否拥有商家", description = "供其它服务校验用户-商家归属")
    @GetMapping("/user-business/check")
    public RestBean<Boolean> checkUserOwnsBusiness(@RequestParam Long userId, @RequestParam Long businessId) {
        boolean owns = userBusinessService.checkUserOwnsBusiness(userId, businessId);
        return RestBean.success(owns);
    }

    /**
     * 管理员禁用商家
     * 该接口用于将指定商家的状态设为禁用（status=0），禁用后商家在客户端不可见，商家端可见但为禁用状态
     * @param businessId 商家ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @Operation(summary = "管理员禁用商家", description = "将商家状态设为禁用")
    @PostMapping("/admin/disable")
    public RestBean<Boolean> disableBusiness(@RequestParam Long businessId) {
        boolean result = businessService.updateBusinessStatus(businessId, 0);
        return result ? RestBean.success(true) : RestBean.failure(400, "禁用失败");
    }

    /**
     * 管理员启用商家
     * 该接口用于将指定商家的状态设为正常（status=1），恢复后商家在客户端可见
     * @param businessId 商家ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @Operation(summary = "管理员启用商家", description = "将商家状态设为正常")
    @PostMapping("/admin/enable")
    public RestBean<Boolean> enableBusiness(@RequestParam Long businessId) {
        boolean result = businessService.updateBusinessStatus(businessId, 1);
        return result ? RestBean.success(true) : RestBean.failure(400, "启用失败");
    }
}
