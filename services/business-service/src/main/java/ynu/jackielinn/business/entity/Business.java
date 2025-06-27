package ynu.jackielinn.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("business")
public class Business implements BaseData {
    @TableId(type = IdType.AUTO)
    Long businessId;
    @TableField("businessName")
    String businessName;
    @TableField("businessAddress")
    String businessAddress;
    @TableField("businessExplain")
    String businessExplain;
    @TableField("businessImg")
    String businessImg;
    @TableField("orderTypeId")
    Integer orderTypeId;
    @TableField("startPrice")
    Double startPrice;
    @TableField("deliveryPrice")
    Double deliveryPrice;
    @TableField("remarks")
    String remarks;
    @TableField("status")
    Integer status; // 1: 正常, 0: 禁用

}
