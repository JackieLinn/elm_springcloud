package ynu.jackielinn.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.jackielinn.common.utils.Pair;
import ynu.jackielinn.orders.entity.OrderDetailed;
import ynu.jackielinn.orders.mapper.OrderDetailedMapper;
import ynu.jackielinn.orders.service.OrderDetailedService;
import ynu.jackielinn.orders.service.feign.CartFeignClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderDetailedServiceImpl extends ServiceImpl<OrderDetailedMapper, OrderDetailed>
        implements OrderDetailedService {

    @Resource
    CartFeignClient cartFeignClient;

    /**
     * 保存订单详细信息
     * 此方法用于将订单中的商品详情保存到数据库中它创建一个OrderDetailed对象，
     * 插入到数据库，并返回插入操作的影响行数
     *
     * @param orderId 订单ID，关联订单表
     * @param foodId 商品ID，关联商品表
     * @param quantity 商品数量
     * @return 返回插入操作影响的行数，表示插入成功与否
     */
    @Override
    public Integer saveInOrderDetailed(Long orderId, Long foodId, Integer quantity) {
        OrderDetailed orderDetailed = new OrderDetailed(null, orderId, foodId, quantity);
        return baseMapper.insert(orderDetailed);
    }

    /**
     * 按照订单详情添加购物车中的商品
     * 此方法的主要功能是将用户购物车中特定商家的商品添加到订单详情中，并清除购物车中对应的商品
     * 它首先从购物车服务中获取用户购物车的商品映射，然后遍历该映射，将每个商品及其数量保存到订单详情中
     * 如果保存失败（即saveInOrderDetailed返回0），则整个方法返回false
     * 在商品成功添加到订单详情后，它会尝试从购物车中删除这些商品如果删除失败，则整个方法返回false
     *
     * @param userId 用户ID，用于标识购物车的拥有者
     * @param businessId 商家ID，用于过滤购物车中的商品，只包含该商家的商品
     * @param orderId 订单ID，用于将购物车商品添加到对应的订单详情中
     * @return 如果所有商品都成功添加到订单详情中，并且购物车中的商品都成功删除，则返回true；否则返回false
     */
    @Override
    public boolean addInOrderDetailed(Long userId, Long businessId, Long orderId) {
        Map<Long, Pair<Long, Integer>> cartMap = cartFeignClient.getCartMap(userId, businessId);
        for (Map.Entry<Long, Pair<Long, Integer>> entry : cartMap.entrySet()) {
            Pair<Long, Integer> pair = entry.getValue();
            Long foodId = pair.getFirst();
            Integer quantity = pair.getSecond();
            Integer result = saveInOrderDetailed(orderId, foodId, quantity);
            if (result == 0) return false;
        }
        for (Long cartId : cartMap.keySet()) {
            Integer deleteResult = cartFeignClient.deleteByCartId(cartId);
            if (deleteResult == 0) return false;
        }
        return true;
    }

    /**
     * 根据订单ID获取食品信息
     * 此方法通过接收一个订单ID来查询数据库中与该订单相关的所有食品详细信息，
     * 并将这些信息整理成一个映射表，其中键是食品ID，值是该食品的购买数量
     *
     * @param orderId 订单ID，用于查询与该订单相关的食品详细信息
     * @return 返回一个映射表，键为食品ID，值为该食品的购买数量
     */
    @Override
    public Map<Long, Integer> getFoodInfoByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetailed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetailed::getOrderId, orderId);
        List<OrderDetailed> orderDetailedList = this.list(queryWrapper);
        return orderDetailedList.stream()
                .collect(Collectors.toMap(
                        OrderDetailed::getFoodId,
                        OrderDetailed::getQuantity
                ));
    }
}
