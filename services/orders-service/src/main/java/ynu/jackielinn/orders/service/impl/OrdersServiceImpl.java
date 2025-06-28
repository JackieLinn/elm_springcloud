package ynu.jackielinn.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import ynu.jackielinn.common.utils.Pair;
import ynu.jackielinn.orders.dto.request.OrdersRO;
import ynu.jackielinn.orders.dto.request.OrdersUpdateRO;
import ynu.jackielinn.orders.dto.request.PaymentRO;
import ynu.jackielinn.orders.dto.response.*;
import ynu.jackielinn.orders.entity.OrderDetailed;
import ynu.jackielinn.orders.entity.Orders;
import ynu.jackielinn.orders.mapper.OrderDetailedMapper;
import ynu.jackielinn.orders.mapper.OrdersMapper;
import ynu.jackielinn.orders.service.OrderDetailedService;
import ynu.jackielinn.orders.service.OrdersService;
import ynu.jackielinn.orders.service.feign.AccountFeignClient;
import ynu.jackielinn.orders.service.feign.BusinessFeignClient;
import ynu.jackielinn.orders.service.feign.FoodFeignClient;
import ynu.jackielinn.common.entity.RestBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ynu.jackielinn.orders.enums.OrderStateEnum;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    @Resource
    OrderDetailedService orderDetailedService;

    @Resource
    BusinessFeignClient businessFeignClient;

    @Resource
    FoodFeignClient foodFeignClient;

    @Resource
    AccountFeignClient accountFeignClient;

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    OrderDetailedMapper orderDetailedMapper;

    /**
     * 创建订单
     * 该方法负责根据提供的订单信息创建一个新的订单记录在数据库中
     * 如果插入成功，返回新创建的订单的订单ID；如果失败，则返回null
     *
     * @param ro 包含订单相关信息的记录对象，如用户ID、商家ID和总价格
     * @return 成功创建订单后返回订单ID，否则返回null
     */
    @GlobalTransactional
    @Override
    public Long createOrders(OrdersRO ro) {
        Orders order = new Orders();
        order.setUserId(ro.getUserId());
        order.setBusinessId(ro.getBusinessId());
        order.setOrderTotal(ro.getTotalPrice());
        order.setOrderState(0);
        order.setDaId(1L);  // 先赋一个默认值，后续再更改
        order.setOrderDate(LocalDateTime.now().toString());
        int insertResult = baseMapper.insert(order);
        if (insertResult > 0) {
            // 插入进行明细表
            Long orderId = order.getOrderId();
            if (orderDetailedService.addInOrderDetailed(ro.getUserId(), ro.getBusinessId(), orderId)) {
                return orderId;
            } else return null;
        } else return null;
    }

    /**
     * 获取订单的业务信息
     * 该方法用于根据订单ID获取订单的详细商家信息，包括商家名称、配送费用和订单总价
     * 它首先根据订单ID获取订单信息，然后根据业务ID获取商家详细信息，并最终组合这些信息返回
     *
     * @param orderId 订单ID，用于查询订单和相关业务信息
     * @return OrdersBusinessVO 返回一个包含业务名称、配送费用和订单总价的订单业务信息对象
     */
    @GlobalTransactional
    @Override
    public OrdersBusinessVO getOrdersBusinessInfo(Long orderId) {
        Orders orders = getById(orderId);
        BusinessVO businessVO = businessFeignClient.listBusinessByBusinessIdRemote(orders.getBusinessId());
        return new OrdersBusinessVO(businessVO.getBusinessName(),
                businessVO.getDeliveryPrice(), orders.getOrderTotal());
    }

    /**
     * 根据订单ID获取订单中的食品信息
     * 此方法从订单详情中提取食品信息，并将其格式化为一个包含食品详情和数量的列表
     * @param orderId 订单ID，用于查询订单中的食品信息
     * @return 返回一个 OrdersFoodVO 对象列表，每个对象包含食品名称、图片、价格和数量
     */
    @GlobalTransactional
    @Override
    public List<OrdersFoodVO> getOrdersFoodInfo(Long orderId) {
        List<OrdersFoodVO> ordersFoodVOList = new ArrayList<>();
        Map<Long, Integer> foodMapInfo = orderDetailedService.getFoodInfoByOrderId(orderId);

        for (Map.Entry<Long, Integer> entry : foodMapInfo.entrySet()) {
            Long foodId = entry.getKey();
            Integer quantity = entry.getValue();
            FoodVO foodVO = foodFeignClient.getFoodByFoodId(foodId);
            if (foodVO != null) {
                OrdersFoodVO ordersFoodVO = new OrdersFoodVO();
                ordersFoodVO.setFoodName(foodVO.getFoodName());
                ordersFoodVO.setFoodImg(foodVO.getFoodImg());
                ordersFoodVO.setFoodPrice(foodVO.getFoodPrice());
                ordersFoodVO.setQuantity(quantity);
                ordersFoodVOList.add(ordersFoodVO);
            }
        }
        return ordersFoodVOList;
    }

    /**
     * 处理订单支付逻辑
     * 本函数主要负责处理订单的支付流程，包括验证订单、扣款和更新订单状态
     * 首先，根据提供的订单ID获取订单信息，如果订单不存在，则直接返回支付失败
     * 接着，调用账户服务进行扣款操作，如果扣款失败，同样返回支付失败
     * 最后，更新订单状态为已支付，并保存更新到数据库，返回支付成功
     * @param ro 包含支付相关信息的记录对象
     * @return 支付是否成功
     */
    @GlobalTransactional
    @Override
    public Boolean ordersPayment(PaymentRO ro) {
        Orders orders = getById(ro.getOrderId());
        if (orders == null) return false;
        // 从请求上下文中获取token
        String token = RequestContextHolder.getRequestAttributes() != null ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization") :
                null;
        if (token == null) return false;
        RestBean<Boolean> payResult = accountFeignClient.pay(ro.getUserId(), orders.getOrderTotal(), token);
        // 检查远程调用是否成功，以及支付结果是否为true
        if (payResult.code() != 200 || !Boolean.TRUE.equals(payResult.data())) return false;
        orders.setOrderState(1);
        int result = baseMapper.updateById(orders);
        return result > 0;
    }

    /**
     * 获取所有已支付订单信息
     * 该方法用于获取所有已支付订单的详细信息，包括订单ID、用户ID、商家ID、订单总价、订单状态和订单日期
     * @return 返回一个包含所有已支付订单信息的列表
     */
    @Override
    public List<OrderListVO> getAllOrderInfoByUserIdAndOrderState(Long userId, Integer orderState) {
        // 1. 查询所有已支付的订单
        QueryWrapper<Orders> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("userId", userId)
                .eq("orderState", orderState);
        List<Orders> orders = ordersMapper.selectList(orderWrapper);
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 获取所有相关的 businessId
        Set<Long> businessIds = orders.stream()
                .map(Orders::getBusinessId)
                .collect(Collectors.toSet());

        // 3. 批量查询商家信息
        Map<Long, Business> businessMap = businessFeignClient.getBusinessInfo(businessIds);

        // 4. 获取所有订单的 orderId
        Set<Long> orderIds = orders.stream()
                .map(Orders::getOrderId)
                .collect(Collectors.toSet());

        // 5. 批量查询订单详细信息
        QueryWrapper<OrderDetailed> detailedWrapper = new QueryWrapper<>();
        detailedWrapper.in("orderId", orderIds);
        List<OrderDetailed> orderDetailedList = orderDetailedMapper.selectList(detailedWrapper);
        if (orderDetailedList.isEmpty()) {
            return Collections.emptyList();
        }

        // 6. 获取所有相关的 foodId
        Set<Long> foodIds = orderDetailedList.stream()
                .map(OrderDetailed::getFoodId)
                .collect(Collectors.toSet());

        // 7. 批量查询食品信息
        Map<Long, Food> foodMap = foodFeignClient.getFoodInfo(foodIds);

        // 8. 按 orderId 分组订单详细信息
        Map<Long, List<OrderDetailed>> orderDetailedMap = orderDetailedList.stream()
                .collect(Collectors.groupingBy(OrderDetailed::getOrderId));

        // 9. 组装 OrderListVO 列表
        List<OrderListVO> orderListVOList = new ArrayList<>();
        for (Orders order : orders) {
            OrderListVO vo = new OrderListVO();
            Business business = businessMap.get(order.getBusinessId());
            vo.setBusinessName(business.getBusinessName());
            vo.setTotalPrice(String.valueOf(order.getOrderTotal()));
            vo.setDeliveryPrice(business.getDeliveryPrice());

            List<OrderDetailed> detailedForOrder = orderDetailedMap.get(order.getOrderId());
            if (detailedForOrder != null) {
                Map<Long, Pair<String, Pair<Integer, Double>>> foodList = new HashMap<>();
                for (OrderDetailed od : detailedForOrder) {
                    Food food = foodMap.get(od.getFoodId());
                    if (food != null) {
                        String foodName = food.getFoodName();
                        Integer quantity = od.getQuantity();
                        Double price = food.getFoodPrice();
                        foodList.put(food.getFoodId(), new Pair<>(foodName, new Pair<>(quantity, price)));
                    }
                }
                vo.setFoodList(foodList);
            }
            orderListVOList.add(vo);
        }

        return orderListVOList;
    }

    /**
     * 获取指定用户的所有订单信息
     *
     * @param userId 用户ID
     * @return 返回一个包含已支付和未支付订单信息的对象
     */
    @GlobalTransactional
    @Override
    public AllOrderListVO getAllOrderInfo(Long userId) {
        AllOrderListVO allOrderListVO = new AllOrderListVO();
        allOrderListVO.setPaidList(getAllOrderInfoByUserIdAndOrderState(userId, 1));
        allOrderListVO.setUnpaidList(getAllOrderInfoByUserIdAndOrderState(userId, 0));
        return allOrderListVO;
    }

    /**
     * 更新订单的 daId
     *
     * @param ro 包含订单ID和新的 daId 的对象
     * @return 返回更新操作的结果，成功则返回 1，失败则返回 0
     */
    @Override
    public Integer updateDaId(OrdersUpdateRO ro) {
        Orders orders = getById(ro.getOrderId());
        orders.setDaId(ro.getDaId());
        return baseMapper.updateById(orders);
    }

    @Override
    public List<Orders> listOrdersByBusinessId(Long userId, Long businessId) {
        Boolean owns = businessFeignClient.checkUserOwnsBusiness(userId, businessId);
        if (!Boolean.TRUE.equals(owns)) throw new RuntimeException("无权查看该商家订单");
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("businessId", businessId);
        return this.list(queryWrapper);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long userId, Long orderId) {
        Orders order = this.getById(orderId);
        if (order == null) return null;
        Boolean owns = businessFeignClient.checkUserOwnsBusiness(userId, order.getBusinessId());
        if (!Boolean.TRUE.equals(owns)) throw new RuntimeException("无权查看该订单");
        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrderId(order.getOrderId());
        vo.setOrderDate(order.getOrderDate());
        vo.setOrderTotal(order.getOrderTotal());
        vo.setOrderState(order.getOrderState());
        BusinessVO businessVO = businessFeignClient.listBusinessByBusinessIdRemote(order.getBusinessId());
        vo.setBusinessName(businessVO.getBusinessName());
        vo.setDeliveryPrice(businessVO.getDeliveryPrice());
        List<OrdersFoodVO> foodList = getOrdersFoodInfo(orderId);
        vo.setFoodList(foodList);
        return vo;
    }

    @Override
    public Boolean acceptOrder(Long userId, Long orderId) {
        Orders order = getById(orderId);
        if (order == null) return false;
        Boolean owns = businessFeignClient.checkUserOwnsBusiness(userId, order.getBusinessId());
        if (!Boolean.TRUE.equals(owns)) throw new RuntimeException("无权操作该订单");
        if (order.getOrderState() != OrderStateEnum.UNPAID.getCode() && order.getOrderState() != OrderStateEnum.PAID.getCode()) {
            return false;
        }
        order.setOrderState(OrderStateEnum.ACCEPTED.getCode());
        return baseMapper.updateById(order) > 0;
    }

    @Override
    public Boolean finishOrder(Long userId, Long orderId) {
        Orders order = getById(orderId);
        if (order == null) return false;
        Boolean owns = businessFeignClient.checkUserOwnsBusiness(userId, order.getBusinessId());
        if (!Boolean.TRUE.equals(owns)) throw new RuntimeException("无权操作该订单");
        if (order.getOrderState() != OrderStateEnum.ACCEPTED.getCode()) {
            return false;
        }
        order.setOrderState(OrderStateEnum.FINISHED.getCode());
        return baseMapper.updateById(order) > 0;
    }

    @Override
    public Boolean rejectOrder(Long userId, Long orderId) {
        Orders order = getById(orderId);
        if (order == null) return false;
        Boolean owns = businessFeignClient.checkUserOwnsBusiness(userId, order.getBusinessId());
        if (!Boolean.TRUE.equals(owns)) throw new RuntimeException("无权操作该订单");
        if (order.getOrderState() != OrderStateEnum.UNPAID.getCode() && order.getOrderState() != OrderStateEnum.PAID.getCode()) {
            return false;
        }
        if (order.getOrderState() == OrderStateEnum.PAID.getCode()) {
            String token = RequestContextHolder.getRequestAttributes() != null ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization") :
                null;
            if (token == null) return false;
            RestBean<Boolean> refundResult = accountFeignClient.refund(order.getUserId(), order.getOrderTotal(), token);
            if (refundResult.code() != 200 || !Boolean.TRUE.equals(refundResult.data())) return false;
        }
        order.setOrderState(OrderStateEnum.CANCELED.getCode());
        return baseMapper.updateById(order) > 0;
    }
}
