package ynu.jackielinn.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.cart.dto.request.CartListRO;
import ynu.jackielinn.cart.dto.request.CartSaveRO;
import ynu.jackielinn.cart.dto.request.CartUpdateRO;
import ynu.jackielinn.cart.dto.response.CartQuantityVO;
import ynu.jackielinn.cart.dto.response.CartVO;
import ynu.jackielinn.cart.entity.Cart;
import ynu.jackielinn.common.utils.Pair;

import java.util.List;
import java.util.Map;

public interface CartService extends IService<Cart> {

    List<CartVO> listCart(CartListRO ro);

    Integer saveCart(CartSaveRO ro);

    Integer updateCart(CartUpdateRO ro);

    Integer removeCart(CartSaveRO ro);

    List<CartQuantityVO> getCartQuantity(Long userId);

    Map<Long, Pair<Long, Integer>> getCartMap(Long userId, Long businessId);

    Integer deleteByCartId(Long cartId);
}
