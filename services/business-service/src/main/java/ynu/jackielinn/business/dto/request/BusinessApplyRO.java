package ynu.jackielinn.business.dto.request;

import lombok.Data;

/**
 * 商家入驻申请请求对象
 * 使用userName而不是applicantId，解决用户未登录时无法获取userId的问题
 */
@Data
public class BusinessApplyRO {
    /**
     * 申请人用户名
     */
    private String userName;
    
    /**
     * 申请商家名称
     */
    private String businessName;
    
    /**
     * 申请商家地址
     */
    private String businessAddress;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 商家简介
     */
    private String businessDesc;
} 