package ynu.jackielinn.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "订单性别")
public class OrdersRO {
    @Schema(description = "用户ID")
    Long userId;
    @Schema(description = "商家ID")
    Long businessId;
    @Schema(description = "总金额")
    double totalPrice;
}
