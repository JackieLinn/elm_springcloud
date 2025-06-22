package ynu.jackielinn.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ynu.jackielinn.common.utils.Pair;

import java.util.Map;

@Data
@Schema(description = "订单列表对象")
public class OrderListVO {
    @Schema(description = "商家名字")
    String businessName;
    @Schema(description = "订单总价格")
    String totalPrice;
    @Schema(description = "配送费")
    Double deliveryPrice;
    @Schema(description = "食物信息三元组（食物id，食物名字，食物数量，食物价格）")
    Map<Long, Pair<String, Pair<Integer, Double>>> foodList;
}
