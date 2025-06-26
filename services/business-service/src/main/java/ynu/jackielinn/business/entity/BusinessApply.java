package ynu.jackielinn.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("business_apply")
public class BusinessApply {
    @TableId(type = IdType.AUTO)
    private Long applyId;
    @TableField("applicantId")
    private Long applicantId;
    @TableField("businessName")
    private String businessName;
    @TableField("businessAddress")
    private String businessAddress;
    @TableField("contactPhone")
    private String contactPhone;
    @TableField("businessDesc")
    private String businessDesc;
    @TableField("applyStatus")
    private Integer applyStatus;
    @TableField("reviewReason")
    private String reviewReason;
    @TableField("applyTime")
    private LocalDateTime applyTime;
    @TableField("reviewTime")
    private LocalDateTime reviewTime;
} 