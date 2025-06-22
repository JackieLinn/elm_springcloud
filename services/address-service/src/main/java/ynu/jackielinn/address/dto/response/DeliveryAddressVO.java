package ynu.jackielinn.address.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "送餐地址对象")
public class DeliveryAddressVO {
    @Schema(description = "送餐地址ID")
    Long daId;
    @Schema(description = "联系人姓名")
    String contactName;
    @Schema(description = "联系人性别")
    Integer contactSex;
    @Schema(description = "联系人电话")
    String contactTel;
    @Schema(description = "地址")
    String address;
}
