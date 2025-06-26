package ynu.jackielinn.business.service;

import ynu.jackielinn.business.entity.BusinessApply;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface BusinessApplyService {
    boolean applyForMerchant(BusinessApply apply);
    BusinessApply getApplyStatus(Long userId);
    IPage<BusinessApply> listApplies(Page<BusinessApply> page, Integer status);
    boolean reviewApply(Long applyId, Integer result, String reviewReason);
} 