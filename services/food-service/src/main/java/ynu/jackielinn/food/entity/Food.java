package ynu.jackielinn.food.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ynu.jackielinn.common.entity.BaseData;

import java.time.LocalDateTime;

@Data
@TableName("food")
@AllArgsConstructor
@NoArgsConstructor
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
    @TableField("foodStatus")
    Integer foodStatus;
    @TableField("isDeleted")
    Integer isDeleted;
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
}
