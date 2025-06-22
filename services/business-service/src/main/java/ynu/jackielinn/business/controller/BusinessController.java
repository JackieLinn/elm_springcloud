package ynu.jackielinn.business.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.service.BusinessService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business")
@Tag(name = "商家相关接口", description = "与商家相关的操作接口")
public class BusinessController {

    @Resource
    BusinessService businessService;

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
}
