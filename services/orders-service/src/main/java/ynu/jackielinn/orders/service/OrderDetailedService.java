package ynu.jackielinn.orders.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.orders.entity.OrderDetailed;

import java.util.Map;

public interface OrderDetailedService extends IService<OrderDetailed> {

    Integer saveInOrderDetailed(Long orderId, Long foodId, Integer quantity);

    Boolean addInOrderDetailed(Long userId, Long businessId, Long orderId);

    Map<Long, Integer> getFoodInfoByOrderId(Long orderId);
}
