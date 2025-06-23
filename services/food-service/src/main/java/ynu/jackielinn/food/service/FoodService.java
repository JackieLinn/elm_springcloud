package ynu.jackielinn.food.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.entity.Food;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FoodService extends IService<Food> {

    List<FoodVO> listFoodByBusinessId(Long businessId);

    FoodVO getFoodByFoodId(Long foodId);

    Map<Long, Food> getFoodInfo(Set<Long> foodIds);
}
