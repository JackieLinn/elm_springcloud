package ynu.jackielinn.food.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.entity.Food;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FoodService extends IService<Food> {

    FoodVO getFoodByFoodId(Long foodId);

    Map<Long, Food> getFoodInfo(Set<Long> foodIds);

    boolean addFood(Food food);

    boolean updateFood(Food food);

    boolean deleteFood(Long foodId);

    boolean updateStatus(Long foodId, Integer foodStatus);

    /**
     * 查询商家所有菜品（商家/管理端用）
     */
    List<FoodVO> listAllFoodByBusinessId(Long businessId);

    /**
     * 查询商家所有上架菜品（客户端用）
     */
    List<FoodVO> listOnShelfFoodByBusinessId(Long businessId);
}
