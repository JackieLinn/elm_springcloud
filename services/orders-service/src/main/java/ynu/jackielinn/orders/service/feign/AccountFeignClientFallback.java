package ynu.jackielinn.orders.service.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountFeignClientFallback implements AccountFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountFeignClientFallback.class);

    @Override
    public Boolean pay(Long userId, Double price) {
        logger.warn("服务降级：无法调用支付服务，用户ID：{}, 金额：{}", userId, price);
        return false;
    }
}
