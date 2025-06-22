package ynu.jackielinn.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单食物对象")
public class OrdersFoodVO {
    @Schema(description = "食物名字")
    String foodName;
    @Schema(description = "食物图片")
    String foodImg;
    @Schema(description = "食物价格")
    Double foodPrice;
    @Schema(description = "食物购买数量")
    Integer quantity;
}
