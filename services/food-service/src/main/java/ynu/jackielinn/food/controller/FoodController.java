package ynu.jackielinn.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.service.FoodService;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@Tag(name = "食物相关接口", description = "与食物相关的操作接口")
public class FoodController {

    @Resource
    FoodService foodService;

    @Operation(summary = "根据商家编号获取食物信息", description = "根据商家编号获取食物信息")
    @GetMapping("/list-food-by-BusinessId")
    public RestBean<List<FoodVO>> listFoodByBusinessId(Long businessId) {
        return RestBean.success(foodService.listFoodByBusinessId(businessId));
    }

    @Operation(summary = "远程调用：根据食物ID获取食物", description = "远程调用：根据食物ID获取食物")
    @GetMapping("/list-food-by-BusinessId")
    public FoodVO getFoodByFoodId(@RequestParam Long foodId) {
        return foodService.getFoodByFoodId(foodId);
    }
}
