package ynu.jackielinn.orders.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("orderdetailed")
@AllArgsConstructor
public class OrderDetailed {
    @TableId(type = IdType.AUTO)
    Long odId;
    @TableField("orderId")
    Long orderId;
    @TableField("foodId")
    Long foodId;
    @TableField("quantity")
    Integer quantity;
}
