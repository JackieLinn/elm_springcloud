package ynu.jackielinn.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商家对象")
public class Business {
    @Schema(description = "商家ID")
    Long businessId;
    @Schema(description = "商家名字")
    String businessName;
    @Schema(description = "商家地址")
    String businessAddress;
    @Schema(description = "商家介绍")
    String businessExplain;
    @Schema(description = "商家图片")
    String businessImg;
    @Schema(description = "商家分类")
    Integer orderTypeId;
    @Schema(description = "起送价格")
    Double startPrice;
    @Schema(description = "商家配送费")
    Double deliveryPrice;
    @Schema(description = "备注")
    String remarks;
}
