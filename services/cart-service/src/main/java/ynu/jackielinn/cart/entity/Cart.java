package ynu.jackielinn.cart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("cart")
@AllArgsConstructor
public class Cart implements BaseData {
    @TableId(type = IdType.AUTO)
    Long cartId;
    @TableField("foodId")
    Long foodId;
    @TableField("businessId")
    Long businessId;
    @TableField("userId")
    Long userId;
    @TableField("quantity")
    Integer quantity;
}
