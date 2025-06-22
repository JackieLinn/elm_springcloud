package ynu.jackielinn.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "购物车更新对象对象")
public class CartUpdateRO {
    @Schema(description = "用户ID")
    Long userId;
    @Schema(description = "商家ID")
    Long businessId;
    @Schema(description = "食品ID")
    Long foodId;
    @Schema(description = "食品数量")
    Integer quantity;
}
