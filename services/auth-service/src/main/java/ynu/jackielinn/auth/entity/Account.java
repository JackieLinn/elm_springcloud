package ynu.jackielinn.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("account")
@AllArgsConstructor
public class Account implements BaseData {
    @TableId(type = IdType.AUTO)
    Long userId;    /*用户编号*/
    @TableField("userName")
    String userName;    /*用户名字*/
    @TableField("password")
    String password;    /*密码*/
    @TableField("userSex")
    Integer userSex;    /*用户性别（1:男, 0: 女）*/
    @TableField("email")
    String email;   /*用户邮箱*/
    @TableField("userImg")
    String userImg; /*用户头像*/
    @TableField("balance")
    Double balance; /*用户余额*/
    @TableField("delTag")
    Integer delTag; /*删除标志（1:正常，0:删除）*/
}
