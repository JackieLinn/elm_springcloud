package ynu.jackielinn.orders.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.orders.dto.request.OrdersRO;
import ynu.jackielinn.orders.dto.request.OrdersUpdateRO;
import ynu.jackielinn.orders.dto.request.PaymentRO;
import ynu.jackielinn.orders.dto.response.AllOrderListVO;
import ynu.jackielinn.orders.dto.response.OrderListVO;
import ynu.jackielinn.orders.dto.response.OrdersBusinessVO;
import ynu.jackielinn.orders.dto.response.OrdersFoodVO;
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
}
