package ynu.jackielinn.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户对象")
public class AccountVO {
    @Schema(description = "用户ID")
    Long userId;
    @Schema(description = "用户名")
    String userName;
    @Schema(description = "用户性别")
    Integer userSex;
    @Schema(description = "邮箱")
    String email;
    @Schema(description = "用户头像")
    String userImg;
}
