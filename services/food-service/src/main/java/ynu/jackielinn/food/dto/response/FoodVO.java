package ynu.jackielinn.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "食物对象")
public class FoodVO {
    @Schema(description = "食物ID")
    Long foodId;
    @Schema(description = "食物名称")
    String foodName;
    @Schema(description = "食物介绍")
    String foodExplain;
    @Schema(description = "食物图片")
    String foodImg;
    @Schema(description = "食物价格")
    Double foodPrice;
}
