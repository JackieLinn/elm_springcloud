package ynu.jackielinn.address.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("deliveryaddress")
@AllArgsConstructor
public class DeliveryAddress implements BaseData {
    @TableId(type = IdType.AUTO)
    Long daId;
    @TableField("contactName")
    String contactName;
    @TableField("contactSex")
    Integer contactSex;
    @TableField("contactTel")
    String contactTel;
    @TableField("address")
    String address;
    @TableField("userId")
    Long userId;
}
