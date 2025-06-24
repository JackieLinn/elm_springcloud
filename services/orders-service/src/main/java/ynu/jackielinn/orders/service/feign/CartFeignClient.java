package ynu.jackielinn.orders.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ynu.jackielinn.common.utils.Pair;

import java.util.Map;

@FeignClient(value = "cart-service", path = "/api/cart", fallback = CartFeignClientFallback.class)
public interface CartFeignClient {

    @GetMapping("/get-cart-map")
    Map<Long, Pair<Long, Integer>> getCartMap(@RequestParam Long userId, @RequestParam Long businessId);

    @PostMapping("/delete-by-cid")
    Integer deleteByCartId(@RequestParam Long cartId);
}
