package ynu.jackielinn.orders.service.feign;

import org.springframework.stereotype.Component;
import ynu.jackielinn.orders.dto.response.FoodVO;

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
}
