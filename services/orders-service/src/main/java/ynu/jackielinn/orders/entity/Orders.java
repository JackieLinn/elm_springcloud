package ynu.jackielinn.orders.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("orders")
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements BaseData {
    @TableId(type = IdType.AUTO)
    Long orderId;
    @TableField("userId")
    Long userId;
    @TableField("businessId")
    Long businessId;
    @TableField("orderDate")
    String orderDate;
    @TableField("orderTotal")
    Double orderTotal;
    @TableField("daId")
    Long daId;
    @TableField("orderState")
    Integer orderState;
}
