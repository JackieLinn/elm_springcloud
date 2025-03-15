package ynu.jackielinn.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("account_role")
@AllArgsConstructor
public class AccountRole {
    @TableId(type = IdType.AUTO)
    Long arId;  /*用户角色编号*/
    @TableField("userId")
    Long userId;    /*用户编号*/
    @TableField("roleId")
    Long roleId;    /*角色编号*/
}
