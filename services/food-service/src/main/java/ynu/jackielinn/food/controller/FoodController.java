package ynu.jackielinn.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.entity.Food;
import ynu.jackielinn.food.service.FoodService;
import ynu.jackielinn.food.service.feign.BusinessFeignClient;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/food")
@Tag(name = "食物相关接口", description = "与食物相关的操作接口")
public class FoodController {

    @Resource
    FoodService foodService;

    @Resource
    BusinessFeignClient businessFeignClient;

    @Operation(summary = "根据商家编号获取上架食物信息（客户端用）", description = "只返回上架菜品")
    @GetMapping("/list-food-by-BusinessId")
    public RestBean<List<FoodVO>> listOnShelfFoodByBusinessId(Long businessId) {
        return RestBean.success(foodService.listOnShelfFoodByBusinessId(businessId));
    }

    @Operation(summary = "根据商家编号获取所有食物信息（商家/管理端用）", description = "返回所有菜品")
    @GetMapping("/list-all-food-by-BusinessId")
    public RestBean<List<FoodVO>> listAllFoodByBusinessId(@RequestParam Long businessId) {
        return RestBean.success(foodService.listAllFoodByBusinessId(businessId));
    }

    @Operation(summary = "远程调用：根据食物ID获取食物", description = "远程调用：根据食物ID获取食物")
    @GetMapping("/list-food-by-FoodId")
    public FoodVO getFoodByFoodId(@RequestParam Long foodId) {
        return foodService.getFoodByFoodId(foodId);
    }

    @Operation(summary = "远程调用：根据食物ID列表获取食物", description = "远程调用：根据食物ID列表获取食物")
    @GetMapping("/get-food-info")
    public Map<Long, Food> getFoodInfo(@RequestParam Set<Long> foodIds) {
        return foodService.getFoodInfo(foodIds);
    }

    @Operation(summary = "新增菜品", description = "商家新增菜品")
    @PostMapping("/add")
    public RestBean<Boolean> addFood(@RequestBody Food food) {
        food.setFoodStatus(0);
        food.setIsDeleted(0);
        return RestBean.success(foodService.save(food));
    }

    @Operation(summary = "修改菜品", description = "商家修改菜品")
    @PostMapping("/update")
    public RestBean<Boolean> updateFood(@RequestBody Food food) {
        return RestBean.success(foodService.updateById(food));
    }

    @Operation(summary = "逻辑删除菜品", description = "商家删除菜品（逻辑删除）")
    @PostMapping("/delete")
    public RestBean<Boolean> deleteFood(@RequestParam Long foodId) {
        Food food = foodService.getById(foodId);
        if (food == null || food.getIsDeleted() != 0) {
            return RestBean.failure(404, "菜品不存在或已删除");
        }
        food.setIsDeleted(1);
        return RestBean.success(foodService.updateById(food));
    }

    @Operation(summary = "上下架菜品", description = "商家上下架菜品")
    @PostMapping("/status")
    public RestBean<Boolean> updateStatus(@RequestParam Long foodId, @RequestParam Integer foodStatus) {
        Food food = foodService.getById(foodId);
        if (food == null || food.getIsDeleted() != 0) {
            return RestBean.failure(404, "菜品不存在或已删除");
        }
        food.setFoodStatus(foodStatus);
        return RestBean.success(foodService.updateById(food));
    }

    /**
     * 管理员下架菜品
     * 该接口用于将指定菜品的状态设为下架（foodStatus=0），下架后客户端和商家端均不可见
     * @param foodId 菜品ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @Operation(summary = "管理员下架菜品", description = "将菜品状态设为下架")
    @PostMapping("/admin/disable")
    public RestBean<Boolean> adminDisableFood(@RequestParam Long foodId) {
        boolean result = foodService.updateStatus(foodId, 0);
        return result ? RestBean.success(true) : RestBean.failure(400, "下架失败");
    }

    /**
     * 管理员恢复菜品上架
     * 该接口用于将指定菜品的状态设为上架（foodStatus=1），恢复后客户端和商家端均可见
     * @param foodId 菜品ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @Operation(summary = "管理员恢复菜品上架", description = "将菜品状态设为上架")
    @PostMapping("/admin/enable")
    public RestBean<Boolean> adminEnableFood(@RequestParam Long foodId) {
        boolean result = foodService.updateStatus(foodId, 1);
        return result ? RestBean.success(true) : RestBean.failure(400, "上架失败");
    }
}
