package ynu.jackielinn.orders.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.common.entity.RestBean;
import ynu.jackielinn.orders.dto.request.OrdersRO;
import ynu.jackielinn.orders.dto.request.OrdersUpdateRO;
import ynu.jackielinn.orders.dto.request.PaymentRO;
import ynu.jackielinn.orders.dto.response.AllOrderListVO;
import ynu.jackielinn.orders.dto.response.OrdersBusinessVO;
import ynu.jackielinn.orders.dto.response.OrdersFoodVO;
import ynu.jackielinn.orders.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单相关接口", description = "与订单相关的操作接口")
public class OrdersController {

    @Resource
    OrdersService ordersService;

    @Operation(summary = "创建订单", description = "创建订单")
    @PostMapping("/create-orders")
    public RestBean<Long> createOrders(@RequestBody OrdersRO ro) {
        return RestBean.success(ordersService.createOrders(ro));
    }

    @Operation(summary = "根据订单编号获取商家信息", description = "根据订单编号获取商家信息")
    @GetMapping("/get-business-info")
    public RestBean<OrdersBusinessVO> getBusinessInfo(Long orderId) {
        return RestBean.success(ordersService.getOrdersBusinessInfo(orderId));
    }

    @Operation(summary = "根据订单编号获取食物信息", description = "根据订单编号获取食物信息")
    @GetMapping("/get-food-info")
    public RestBean<List<OrdersFoodVO>> getFoodInfo(Long orderId) {
        return RestBean.success(ordersService.getOrdersFoodInfo(orderId));
    }

    @Operation(summary = "支付操作", description = "支付操作")
    @PostMapping("/payment")
    public RestBean<Boolean> ordersPayment(@RequestBody PaymentRO ro) {
        return RestBean.success(ordersService.ordersPayment(ro));
    }

    @Operation(summary = "获取所有订单信息", description = "获取所有订单信息")
    @GetMapping("/get-all-order-info")
    public RestBean<AllOrderListVO> getAllOrderInfo(Long userId) {
        return RestBean.success(ordersService.getAllOrderInfo(userId));
    }

    @Operation(summary = "更新送餐地址", description = "更新送餐地址")
    @PostMapping("/update-address")
    public RestBean<Integer> updateDaId(@RequestBody OrdersUpdateRO ro) {
        return RestBean.success(ordersService.updateDaId(ro));
    }
}
