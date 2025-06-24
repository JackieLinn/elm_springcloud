package ynu.jackielinn.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.jackielinn.cart.dto.request.CartListRO;
import ynu.jackielinn.cart.dto.request.CartSaveRO;
import ynu.jackielinn.cart.dto.request.CartUpdateRO;
import ynu.jackielinn.cart.dto.response.CartQuantityVO;
import ynu.jackielinn.cart.dto.response.CartVO;
import ynu.jackielinn.cart.entity.Cart;
import ynu.jackielinn.cart.mapper.CartMapper;
import ynu.jackielinn.cart.service.CartService;
import ynu.jackielinn.common.utils.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    CartMapper cartMapper;

    /**
     * 根据用户ID和商家ID查询购物车列表
     *
     * @param ro 包含用户ID和商家ID的请求对象
     * @return 购物车列表的视图对象列表
     * @throws IllegalArgumentException 如果用户ID或商家ID为空
     */
    @Override
    public List<CartVO> listCart(CartListRO ro) {
        if (ro == null || ro.getUserId() == null || ro.getBusinessId() == null) {
            throw new IllegalArgumentException("User ID and Business ID cannot be null");
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", ro.getUserId())
                .eq("businessId", ro.getBusinessId());
        List<Cart> carts = baseMapper.selectList(queryWrapper);
        return carts.stream()
                .map(c -> c.asViewObject(CartVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 保存购物车信息
     *
     * @param ro 包含用户ID、商家ID和食品ID的请求对象
     * @return 插入操作影响的行数
     * @throws IllegalArgumentException 如果用户ID、商家ID或食品ID为空
     */
    @Override
    public Integer saveCart(CartSaveRO ro) {
        if (ro == null || ro.getUserId() == null || ro.getBusinessId() == null || ro.getFoodId() == null) {
            throw new IllegalArgumentException("User ID, Business ID, and Food ID cannot be null");
        }
        Cart cart = new Cart(null, ro.getFoodId(), ro.getBusinessId(), ro.getUserId(), 1);
        return baseMapper.insert(cart);
    }

    /**
     * 更新购物车信息
     *
     * @param ro 包含用户ID、商家ID、食品ID和数量的请求对象
     * @return 更新操作影响的行数
     * @throws IllegalArgumentException 如果用户ID、商家ID、食品ID或数量为空，或者数量为负数
     */
    @Override
    public Integer updateCart(CartUpdateRO ro) {
        if (ro == null || ro.getUserId() == null || ro.getBusinessId() == null || ro.getFoodId() == null || ro.getQuantity() == null) {
            throw new IllegalArgumentException("User ID, Business ID, Food ID, and Quantity cannot be null");
        }
        if (ro.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", ro.getUserId())
                .eq("businessId", ro.getBusinessId())
                .eq("foodId", ro.getFoodId())
                .set("quantity", ro.getQuantity());
        return baseMapper.update(null, updateWrapper);
    }

    /**
     * 从购物车中移除商品
     *
     * @param ro 包含用户ID、商家ID和食品ID的请求对象
     * @return 删除操作影响的行数
     * @throws IllegalArgumentException 如果用户ID、商家ID或食品ID为空
     */
    @Override
    public Integer removeCart(CartSaveRO ro) {
        if (ro == null || ro.getUserId() == null || ro.getBusinessId() == null || ro.getFoodId() == null) {
            throw new IllegalArgumentException("User ID, Business ID, and Food ID cannot be null");
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", ro.getUserId())
                .eq("businessId", ro.getBusinessId())
                .eq("foodId", ro.getFoodId());
        return baseMapper.delete(queryWrapper);
    }

    /**
     * 根据用户ID获取购物车商品数量信息
     * 此方法通过调用cartMapper的getCartQuantityByUserId方法来获取用户购物车的商品数量信息
     * 主要用于在用户界面展示购物车商品总数，以便用户了解购物车内的商品情况
     *
     * @param userId 用户ID，用于查询特定用户的购物车商品数量信息
     * @return 返回一个CartQuantityVO对象列表，每个对象包含特定用户购物车的商品数量信息
     */
    @Override
    public List<CartQuantityVO> getCartQuantity(Long userId) {
        return cartMapper.getCartQuantityByUserId(userId);
    }

    /**
     * 根据用户ID和商家ID获取购物车映射
     * 此方法的作用是查询指定用户在特定商家的购物车信息，并将查询结果以Map形式返回，
     * 其中Map的键是购物车项ID，值是一个Pair对象，包含食物ID和数量
     *
     * @param userId 用户ID，用于筛选查询结果
     * @param businessId 商家ID，用于筛选查询结果
     * @return 返回一个Map，键为购物车项ID，值为一个Pair对象，包含食物ID和数量
     */
    @Override
    public Map<Long, Pair<Long, Integer>> getCartMap(Long userId, Long businessId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getBusinessId, businessId);
        List<Cart> cartList = this.list(queryWrapper);

        // 将查询结果转换成 Map<Long, Pair<Long, Integer>>
        Map<Long, Pair<Long, Integer>> resultMap = new HashMap<>();
        for (Cart cart : cartList) {
            resultMap.put(cart.getCartId(), new Pair<>(cart.getFoodId(), cart.getQuantity()));
        }
        return resultMap;
    }

    /**
     * 根据购物车ID删除购物车记录
     *
     * @param cartId 购物车ID，用于定位要删除的购物车记录
     * @return 返回删除操作的影响行数，如果返回1表示删除成功，否则可能表示删除失败或未找到对应的购物车记录
     */
    @Override
    public Integer deleteByCartId(Long cartId) {
        System.out.println("删除购物车记录：" + cartId);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getCartId, cartId);
        return baseMapper.delete(queryWrapper);
    }
}
