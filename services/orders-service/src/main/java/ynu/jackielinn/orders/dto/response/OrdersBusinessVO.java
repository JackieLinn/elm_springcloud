package ynu.jackielinn.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "订单商家对象")
public class OrdersBusinessVO {
    @Schema(description = "商家名字")
    String businessName;
    @Schema(description = "配送费")
    Double deliveryPrice;
    @Schema(description = "订单总金额")
    Double totalPrice;
}
