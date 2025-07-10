package ynu.jackielinn.orders.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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
import ynu.jackielinn.orders.dto.response.OrderDetailVO;
import ynu.jackielinn.orders.entity.Orders;
import ynu.jackielinn.orders.service.OrdersService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单相关接口", description = "与订单相关的操作接口")
public class OrdersController {

    private static final Logger logger = Logger.getLogger(OrdersController.class.getName());

    @Resource
    OrdersService ordersService;

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "创建订单", description = "创建订单")
    @PostMapping("/create-orders")
    public RestBean<Long> createOrders(@RequestBody OrdersRO ro) {
        return RestBean.success(ordersService.createOrders(ro));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "根据订单编号获取商家信息", description = "根据订单编号获取商家信息")
    @GetMapping("/get-business-info")
    public RestBean<OrdersBusinessVO> getBusinessInfo(Long orderId) {
        return RestBean.success(ordersService.getOrdersBusinessInfo(orderId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "根据订单编号获取食物信息", description = "根据订单编号获取食物信息")
    @GetMapping("/get-food-info")
    public RestBean<List<OrdersFoodVO>> getFoodInfo(Long orderId) {
        return RestBean.success(ordersService.getOrdersFoodInfo(orderId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "支付操作", description = "支付操作")
    @PostMapping("/payment")
    public RestBean<Boolean> ordersPayment(@RequestBody PaymentRO ro) {
        return RestBean.success(ordersService.ordersPayment(ro));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "获取所有订单信息", description = "获取所有订单信息")
    @GetMapping("/get-all-order-info")
    public RestBean<AllOrderListVO> getAllOrderInfo(Long userId) {
        return RestBean.success(ordersService.getAllOrderInfo(userId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "更新送餐地址", description = "更新送餐地址")
    @PostMapping("/update-address")
    public RestBean<Integer> updateDaId(@RequestBody OrdersUpdateRO ro) {
        return RestBean.success(ordersService.updateDaId(ro));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "商家端：根据商家ID查询订单列表", description = "商家端用，返回该商家所有订单")
    @GetMapping("/list-by-business")
    public RestBean<List<Orders>> listOrdersByBusinessId(@RequestParam Long userId, @RequestParam Long businessId) {
        return RestBean.success(ordersService.listOrdersByBusinessId(userId, businessId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "商家端：订单详情", description = "根据订单ID获取订单详情")
    @GetMapping("/detail")
    public RestBean<OrderDetailVO> getOrderDetail(@RequestParam Long userId, @RequestParam Long orderId) {
        return RestBean.success(ordersService.getOrderDetail(userId, orderId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "商家端：接单", description = "商家接单")
    @PostMapping("/accept")
    public RestBean<Boolean> acceptOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        return RestBean.success(ordersService.acceptOrder(userId, orderId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "商家端：完成订单", description = "商家完成订单")
    @PostMapping("/finish")
    public RestBean<Boolean> finishOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        return RestBean.success(ordersService.finishOrder(userId, orderId));
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "商家端：拒单", description = "商家拒单，已支付需退款")
    @PostMapping("/reject")
    public RestBean<Boolean> rejectOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        return RestBean.success(ordersService.rejectOrder(userId, orderId));
    }

    /**
     * 管理员端：按商家ID分页查订单
     */
    @CircuitBreaker(name = "backendA", fallbackMethod = "circuitBreakerFallback")
    @RateLimiter(name = "rateLimiterA", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "bulkheadA", fallbackMethod = "bulkheadFallback")
    @Operation(summary = "管理员端：按商家ID分页查订单", description = "支持按订单状态筛选")
    @GetMapping("/admin/list-by-business")
    public RestBean<IPage<Orders>> adminListOrdersByBusinessId(@RequestParam Long businessId,
                                                              @RequestParam int pageNum,
                                                              @RequestParam int pageSize,
                                                              @RequestParam(required = false) Integer orderState) {
        return RestBean.success(ordersService.adminListOrdersByBusinessId(businessId, pageNum, pageSize, orderState));
    }

    // ==================== 统一降级方法 ====================

    /**
     * Circuit Breaker 统一降级方法
     * 适用于所有接口的熔断降级
     */
    public Object circuitBreakerFallback(Object... args) {
        logger.warning("Circuit Breaker 熔断触发");
        return RestBean.failure(503, "服务暂时不可用，请稍后重试");
    }

    /**
     * Rate Limiter 统一降级方法
     * 适用于所有接口的限流降级
     */
    public Object rateLimiterFallback(Object... args) {
        logger.warning("Rate Limiter 限流触发");
        return RestBean.failure(429, "请求过于频繁，请稍后重试");
    }

    /**
     * Bulkhead 统一降级方法
     * 适用于所有接口的舱壁隔离降级
     */
    public Object bulkheadFallback(Object... args) {
        logger.warning("Bulkhead 舱壁隔离触发");
        return RestBean.failure(503, "服务繁忙，请稍后重试");
    }
}
