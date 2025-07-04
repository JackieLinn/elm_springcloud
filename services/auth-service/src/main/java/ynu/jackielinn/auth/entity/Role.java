package ynu.jackielinn.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("role")
@AllArgsConstructor
public class Role implements BaseData {
    @TableId(type = IdType.AUTO)
    Long roleId;    /*角色编号*/
    @TableField("roleName")
    String roleName;    /*角色名字*/
}
