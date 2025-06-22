package ynu.jackielinn.address.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "送餐地址保存对象")
public class DeliveryAddressSaveRO {
    @Schema(description = "联系人姓名")
    String contactName;
    @Schema(description = "联系人性别")
    Integer contactSex;
    @Schema(description = "联系人电话")
    String contactTel;
    @Schema(description = "地址")
    String address;
    @Schema(description = "用户ID")
    Long userId;
}
