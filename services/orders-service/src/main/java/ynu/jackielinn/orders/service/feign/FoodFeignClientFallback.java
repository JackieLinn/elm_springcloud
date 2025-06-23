package ynu.jackielinn.orders.service.feign;

import org.springframework.stereotype.Component;
import ynu.jackielinn.orders.dto.response.Food;
import ynu.jackielinn.orders.dto.response.FoodVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FoodFeignClientFallback implements FoodFeignClient {

    @Override
    public FoodVO getFoodByFoodId(Long foodId) {
        FoodVO fallbackVO = new FoodVO();
        fallbackVO.setFoodId(foodId);
        fallbackVO.setFoodName("服务降级");
        fallbackVO.setFoodExplain("服务降级");
        fallbackVO.setFoodImg("服务降级");
        fallbackVO.setFoodPrice(0.0);
        return fallbackVO;
    }

    @Override
    public Map<Long, Food> getFoodInfo(Set<Long> foodIds) {
        Map<Long, Food> fallbackMap = new HashMap<>();
        for (Long id : foodIds) {
            Food food = new Food();
            food.setFoodId(id);
            food.setFoodName("服务降级");
            food.setFoodExplain("服务降级");
            food.setFoodImg("服务降级");
            food.setFoodPrice(0.0);
            food.setBusinessId(0L);
            food.setRemarks("服务降级");
            fallbackMap.put(id, food);
        }
        return fallbackMap;
    }
}
