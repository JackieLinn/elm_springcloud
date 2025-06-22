package ynu.jackielinn.cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "购物车数量对象")
public class CartQuantityVO {
    @Schema(description = "商家ID")
    Long businessId;
    @Schema(description = "购物车中食物数量")
    Integer quantity;
}
