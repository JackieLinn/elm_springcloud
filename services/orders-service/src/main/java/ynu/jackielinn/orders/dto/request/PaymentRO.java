package ynu.jackielinn.orders.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "支付对象")
public class PaymentRO {
    @Schema(description = "用户ID")
    Long userId;
    @Schema(description = "订单ID")
    Long orderId;
}
