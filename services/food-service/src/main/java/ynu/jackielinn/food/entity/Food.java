package ynu.jackielinn.food.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import ynu.jackielinn.common.entity.BaseData;

@Data
@TableName("food")
@AllArgsConstructor
public class Food implements BaseData {
    @TableId(type = IdType.AUTO)
    Long foodId;
    @TableField("foodName")
    String foodName;
    @TableField("foodExplain")
    String foodExplain;
    @TableField("foodImg")
    String foodImg;
    @TableField("foodPrice")
    Double foodPrice;
    @TableField("businessId")
    Long businessId;
    @TableField("remarks")
    String remarks;
}
