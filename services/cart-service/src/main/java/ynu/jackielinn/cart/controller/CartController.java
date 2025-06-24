package ynu.jackielinn.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.cart.dto.request.CartListRO;
import ynu.jackielinn.cart.dto.request.CartSaveRO;
import ynu.jackielinn.cart.dto.request.CartUpdateRO;
import ynu.jackielinn.cart.dto.response.CartQuantityVO;
import ynu.jackielinn.cart.dto.response.CartVO;
import ynu.jackielinn.cart.service.CartService;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.common.utils.Pair;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "购物车相关接口", description = "与购物车相关的操作接口")
public class CartController {

    @Resource
    CartService cartService;

    @Operation(summary = "获取购物车信息", description = "获取购物车信息")
    @PostMapping("/list-cart")
    public RestBean<List<CartVO>> listCart(@RequestBody CartListRO ro) {
        return RestBean.success(cartService.listCart(ro));
    }

    @Operation(summary = "保存购物车信息", description = "保存购物车信息")
    @PostMapping("/save-cart")
    public RestBean<Integer> saveCart(@RequestBody CartSaveRO ro) {
        return RestBean.success(cartService.saveCart(ro));
    }

    @Operation(summary = "更新购物车信息", description = "更新购物车信息")
    @PostMapping("/update-cart")
    public RestBean<Integer> updateCart(@RequestBody CartUpdateRO ro) {
        return RestBean.success(cartService.updateCart(ro));
    }

    @Operation(summary = "移除购物车信息", description = "移除购物车信息")
    @PostMapping("/remove-cart")
    public RestBean<Integer> removeCart(@RequestBody CartSaveRO ro) {
        return RestBean.success(cartService.removeCart(ro));
    }

    @Operation(summary = "获取购物车数量", description = "获取购物车数量")
    @GetMapping("/get-cart-quantity")
    public RestBean<List<CartQuantityVO>> getCartQuantity(Long userId) {
        return RestBean.success(cartService.getCartQuantity(userId));
    }

    @Operation(summary = "远程调用：获取购物车映射", description = "远程调用：获取购物车映射")
    @GetMapping("/get-cart-map")
    public Map<Long, Pair<Long, Integer>> getCartMap(@RequestParam Long userId, @RequestParam Long businessId) {
        return cartService.getCartMap(userId, businessId);
    }

    @Operation(summary = "远程调用：删除购物车记录", description = "远程调用：删除购物车记录")
    @PostMapping("/delete-by-cid")
    public Integer deleteByCartId(@RequestParam Long cartId) {
        return cartService.deleteByCartId(cartId);
    }
}
