package ynu.jackielinn.business.service;

import ynu.jackielinn.business.dto.request.BusinessApplyRO;
import ynu.jackielinn.business.entity.BusinessApply;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BusinessApplyService {
    boolean applyForMerchant(BusinessApply apply);
    
    /**
     * 使用BusinessApplyRO提交商家入驻申请
     * 通过userName查询userId，然后调用原有的申请逻辑
     * @param applyRO 申请请求对象
     * @return 申请是否成功
     */
    boolean applyForMerchantByUserName(BusinessApplyRO applyRO);
    
    BusinessApply getApplyStatus(Long userId);
    IPage<BusinessApply> listApplies(Page<BusinessApply> page, Integer status);
    boolean reviewApply(Long applyId, Integer result, String reviewReason);
    List<BusinessApply> getAppliesByUserId(Long userId);
} 