package ynu.jackielinn.orders.service.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ynu.jackielinn.common.entity.RestBean;

public class AccountFeignClientFallback implements AccountFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountFeignClientFallback.class);

    @Override
    public RestBean<Boolean> pay(Long userId, Double price, String token) {
        logger.warn("服务降级：无法调用支付服务，用户ID：{}, 金额：{}", userId, price);
        return RestBean.failure(503, "支付服务当前不可用");
    }

    @Override
    public RestBean<Boolean> refund(Long userId, Double price, String token) {
        logger.warn("服务降级：无法调用退款服务，用户ID：{}, 金额：{}", userId, price);
        return RestBean.failure(503, "退款服务当前不可用");
    }
}
