package ynu.jackielinn.cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "购物车对象")
public class CartVO {
    @Schema(description = "购物车ID")
    Long cartId;
    @Schema(description = "食物ID")
    Long foodId;
    @Schema(description = "食物数量")
    Integer quantity;
}
