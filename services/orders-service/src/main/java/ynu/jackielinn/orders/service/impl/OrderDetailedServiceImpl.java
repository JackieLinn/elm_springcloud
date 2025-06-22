package ynu.jackielinn.orders.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.orders.entity.OrderDetailed;
import ynu.jackielinn.orders.mapper.OrderDetailedMapper;
import ynu.jackielinn.orders.service.OrderDetailedService;

import java.util.Map;

@Service
public class OrderDetailedServiceImpl extends ServiceImpl<OrderDetailedMapper, OrderDetailed>
        implements OrderDetailedService {

    @Override
    public Integer saveInOrderDetailed(Long orderId, Long foodId, Integer quantity) {
        return 0;
    }

    @Override
    public boolean addInOrderDetailed(Long userId, Long businessId, Long orderId) {
        return false;
    }

    @Override
    public Map<Long, Integer> getFoodInfoByOrderId(Long orderId) {
        return Map.of();
    }
}
