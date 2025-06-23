package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.orders.dto.response.FoodVO;

@FeignClient(value = "food-service", path = "/api/food", fallback = FoodFeignClientFallback.class)
public interface FoodFeignClient {

    @GetMapping("/list-food-by-BusinessId")
    FoodVO getFoodByFoodId(@RequestParam Long foodId);
}
