package ynu.jackielinn.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "订单更新对象")
public class OrdersUpdateRO {
    @Schema(description = "订单ID")
    Long orderId;
    @Schema(description = "送餐地址ID")
    Long daId;
}
