package ynu.jackielinn.orders.service.feign;

import org.springframework.stereotype.Component;
import ynu.jackielinn.common.utils.Pair;
import java.util.Collections;
import java.util.Map;

@Component
public class CartFeignClientFallback implements CartFeignClient {

    @Override
    public Map<Long, Pair<Long, Integer>> getCartMap(Long userId, Long businessId) {
        // 熔断时返回空map
        return Collections.emptyMap();
    }

    @Override
    public Integer deleteByCartId(Long cartId) {
        // 熔断时返回0，表示删除失败
        return 0;
    }
}
