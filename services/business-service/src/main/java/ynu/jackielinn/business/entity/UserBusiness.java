package ynu.jackielinn.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_business")
public class UserBusiness {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("businessId")
    private Long businessId;
} 