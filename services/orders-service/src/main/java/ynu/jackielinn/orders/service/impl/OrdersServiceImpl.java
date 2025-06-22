package ynu.jackielinn.orders.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.orders.dto.request.OrdersRO;
import ynu.jackielinn.orders.dto.request.OrdersUpdateRO;
import ynu.jackielinn.orders.dto.request.PaymentRO;
import ynu.jackielinn.orders.dto.response.AllOrderListVO;
import ynu.jackielinn.orders.dto.response.OrderListVO;
import ynu.jackielinn.orders.dto.response.OrdersBusinessVO;
import ynu.jackielinn.orders.dto.response.OrdersFoodVO;
import ynu.jackielinn.orders.entity.Orders;
import ynu.jackielinn.orders.mapper.OrdersMapper;
import ynu.jackielinn.orders.service.OrdersService;

import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    @Override
    public Long createOrders(OrdersRO ro) {
        return 0L;
    }

    @Override
    public OrdersBusinessVO getOrdersBusinessInfo(Long orderId) {
        return null;
    }

    @Override
    public List<OrdersFoodVO> getOrdersFoodInfo(Long orderId) {
        return List.of();
    }

    @Override
    public Boolean ordersPayment(PaymentRO ro) {
        return null;
    }

    @Override
    public List<OrderListVO> getAllOrderInfoByUserIdAndOrderState(Long userId, Integer orderState) {
        return List.of();
    }

    @Override
    public AllOrderListVO getAllOrderInfo(Long userId) {
        return null;
    }

    @Override
    public Integer updateDaId(OrdersUpdateRO ro) {
        return 0;
    }
}
