package ynu.jackielinn.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ynu.jackielinn.business.entity.BusinessApply;
import ynu.jackielinn.business.mapper.BusinessApplyMapper;
import ynu.jackielinn.business.service.BusinessApplyService;
import ynu.jackielinn.business.service.feign.AccountFeignClient;

@Service
public class BusinessApplyServiceImpl implements BusinessApplyService {
    @Resource
    private BusinessApplyMapper businessApplyMapper;

    @Resource
    private AccountFeignClient accountFeignClient;

    /**
     * 商家提交入驻申请
     * @param apply 商家入驻申请信息
     * @return 提交成功返回true，已提交过或已存在待审核申请返回false
     */
    @Override
    public boolean applyForMerchant(BusinessApply apply) {
        // 校验是否已提交过申请或已是商家
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        wrapper.eq("applicantId", apply.getApplicantId()).eq("applyStatus", 0);
        if (businessApplyMapper.selectCount(wrapper) > 0) {
            // 已有待审核申请
            return false;
        }
        // 设置初始状态和时间
        apply.setApplyStatus(0); // 0:待审核
        apply.setApplyTime(java.time.LocalDateTime.now());
        return businessApplyMapper.insert(apply) > 0;
    }

    /**
     * 查询当前用户的最新入驻申请状态
     * @param userId 用户ID
     * @return 最新一条入驻申请记录，若无则返回null
     */
    @Override
    public BusinessApply getApplyStatus(Long userId) {
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        wrapper.eq("applicantId", userId).orderByDesc("applyTime").last("limit 1");
        return businessApplyMapper.selectOne(wrapper);
    }

    /**
     * 管理员分页查看入驻申请列表，可按状态筛选
     * @param page 分页对象
     * @param status 申请状态（可选，null为全部）
     * @return 分页后的入驻申请列表
     */
    @Override
    public IPage<BusinessApply> listApplies(Page<BusinessApply> page, Integer status) {
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("applyStatus", status);
        }
        return businessApplyMapper.selectPage(page, wrapper.orderByDesc("applyTime"));
    }

    /**
     * 管理员审核入驻申请，更新状态和审核意见
     * @param applyId 申请ID
     * @param result 审核结果（1:通过, 2:驳回）
     * @param reviewReason 审核意见
     * @return 审核成功返回true，失败返回false
     */
    @Override
    public boolean reviewApply(Long applyId, Integer result, String reviewReason) {
        BusinessApply apply = businessApplyMapper.selectById(applyId);
        if (apply == null || apply.getApplyStatus() != 0) {
            // 申请不存在或已审核
            return false;
        }
        apply.setApplyStatus(result); // 1:通过, 2:驳回
        apply.setReviewReason(reviewReason);
        apply.setReviewTime(java.time.LocalDateTime.now());
        int updated = businessApplyMapper.updateById(apply);
        // 审核通过后远程调用auth-service升级用户角色为商家
        if (result == 1) {
            // 获取当前请求的token
            String token = null;
            if (RequestContextHolder.getRequestAttributes() != null) {
                token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest().getHeader("Authorization");
            }
            if (token != null) {
                try {
                    accountFeignClient.updateRole(apply.getApplicantId(), 2, token);
                } catch (Exception e) {
                    // 可加日志记录异常
                }
            }
        }
        return updated > 0;
    }
} 