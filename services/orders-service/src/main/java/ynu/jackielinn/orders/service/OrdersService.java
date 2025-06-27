package ynu.jackielinn.orders.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.orders.dto.request.OrdersRO;
import ynu.jackielinn.orders.dto.request.OrdersUpdateRO;
import ynu.jackielinn.orders.dto.request.PaymentRO;
import ynu.jackielinn.orders.dto.response.AllOrderListVO;
import ynu.jackielinn.orders.dto.response.OrderListVO;
import ynu.jackielinn.orders.dto.response.OrdersBusinessVO;
import ynu.jackielinn.orders.dto.response.OrdersFoodVO;
import ynu.jackielinn.orders.dto.response.OrderDetailVO;
import ynu.jackielinn.orders.entity.Orders;

import java.util.List;

public interface OrdersService extends IService<Orders> {

    Long createOrders(OrdersRO ro);

    OrdersBusinessVO getOrdersBusinessInfo(Long orderId);

    List<OrdersFoodVO> getOrdersFoodInfo(Long orderId);

    Boolean ordersPayment(PaymentRO ro);

    List<OrderListVO> getAllOrderInfoByUserIdAndOrderState(Long userId, Integer orderState);

    AllOrderListVO getAllOrderInfo(Long userId);

    Integer updateDaId(OrdersUpdateRO ro);

    /**
     * 商家端：根据商家ID查询订单列表
     * @param userId 用户ID
     * @param businessId 商家ID
     * @return 订单列表
     */
    List<Orders> listOrdersByBusinessId(Long userId, Long businessId);

    /**
     * 商家端：订单详情
     */
    OrderDetailVO getOrderDetail(Long userId, Long orderId);

    /**
     * 商家端：接单
     */
    Boolean acceptOrder(Long userId, Long orderId);

    /**
     * 商家端：完成订单
     */
    Boolean finishOrder(Long userId, Long orderId);

    /**
     * 商家端：拒单（如已支付需退款）
     */
    Boolean rejectOrder(Long userId, Long orderId);
}
