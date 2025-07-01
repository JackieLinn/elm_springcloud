package ynu.jackielinn.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ynu.jackielinn.business.dto.request.BusinessApplyRO;
import ynu.jackielinn.business.entity.Business;
import ynu.jackielinn.business.entity.BusinessApply;
import ynu.jackielinn.business.entity.UserBusiness;
import ynu.jackielinn.business.mapper.BusinessApplyMapper;
import ynu.jackielinn.business.service.BusinessApplyService;
import ynu.jackielinn.business.service.feign.AccountFeignClient;
import ynu.jackielinn.business.service.UserBusinessService;
import ynu.jackielinn.business.service.BusinessService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.List;

@Service
public class BusinessApplyServiceImpl implements BusinessApplyService {
    @Resource
    private BusinessApplyMapper businessApplyMapper;

    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private BusinessService businessService;

    /**
     * 商家提交入驻申请
     * @param apply 商家入驻申请信息
     * @return 提交成功返回true，已提交过或已存在待审核申请返回false
     */
    @Override
    public boolean applyForMerchant(BusinessApply apply) {
        // 校验同一用户是否对同一商家名有待审核申请
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        wrapper.eq("applicantId", apply.getApplicantId())
               .eq("businessName", apply.getBusinessName())
               .eq("applyStatus", 0);
        if (businessApplyMapper.selectCount(wrapper) > 0) {
            // 已有待审核的同名商家申请
            return false;
        }
        // 设置初始状态和时间
        apply.setApplyStatus(0); // 0:待审核
        apply.setApplyTime(java.time.LocalDateTime.now());
        return businessApplyMapper.insert(apply) > 0;
    }

    /**
     * 使用BusinessApplyRO提交商家入驻申请
     * 通过userName查询userId，然后调用原有的申请逻辑
     * @param applyRO 申请请求对象
     * @return 申请是否成功
     */
    @Override
    public boolean applyForMerchantByUserName(BusinessApplyRO applyRO) {
        // 1. 通过userName查询userId
        RestBean<Long> userIdResponse = accountFeignClient.getUserIdByUserName(applyRO.getUserName());
        if (userIdResponse == null || userIdResponse.data() == null) {
            // 用户不存在
            return false;
        }
        Long userId = userIdResponse.data();

        // 2. 校验同一用户是否对同一商家名有待审核申请
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        wrapper.eq("applicantId", userId)
               .eq("businessName", applyRO.getBusinessName())
               .eq("applyStatus", 0);
        if (businessApplyMapper.selectCount(wrapper) > 0) {
            // 已有待审核的同名商家申请
            return false;
        }

        // 3. 创建BusinessApply对象
        BusinessApply apply = new BusinessApply();
        apply.setApplicantId(userId);
        apply.setBusinessName(applyRO.getBusinessName());
        apply.setBusinessAddress(applyRO.getBusinessAddress());
        apply.setContactPhone(applyRO.getContactPhone());
        apply.setBusinessDesc(applyRO.getBusinessDesc());
        apply.setApplyStatus(0); // 0:待审核
        apply.setApplyTime(java.time.LocalDateTime.now());

        // 4. 保存申请
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
            try {
                // 获取当前请求的token
                String token = null;
                if (RequestContextHolder.getRequestAttributes() != null) {
                    token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest().getHeader("Authorization");
                }
                
                if (token != null) {
                    // 调用远程服务升级用户角色为商家（roleId=2）
                    RestBean<Boolean> upgradeResult = accountFeignClient.updateRole(apply.getApplicantId(), 2, token);
                    if (upgradeResult == null || !Boolean.TRUE.equals(upgradeResult.data())) {
                        // 角色升级失败，记录日志但不影响审核流程
                        System.err.println("Failed to upgrade user role for userId: " + apply.getApplicantId());
                    }
                } else {
                    System.err.println("No Authorization token found, cannot upgrade user role");
                }
            } catch (Exception e) {
                // 远程调用异常，记录日志但不影响审核流程
                System.err.println("Exception occurred while upgrading user role: " + e.getMessage());
            }
            
            // 1. 新增商家
            Business business = new Business();
            business.setBusinessName(apply.getBusinessName());
            business.setBusinessAddress(apply.getBusinessAddress());
            business.setBusinessExplain(apply.getBusinessDesc());
            business.setBusinessImg("http://example.com/img.jpg"); // 可根据实际情况设置
            business.setOrderTypeId(1); // 可根据实际情况设置
            business.setStartPrice(0.0); // 可根据实际情况设置
            business.setDeliveryPrice(0.0); // 可根据实际情况设置
            business.setRemarks(null); // 可根据实际情况设置
            businessService.save(business); // 保存后 businessId 自动回填
            Long businessId = business.getBusinessId();
            
            // 2. 关联 user_business
            if (apply.getApplicantId() != null && businessId != null) {
                UserBusiness userBusiness = new UserBusiness(null, apply.getApplicantId(), businessId);
                userBusinessService.save(userBusiness);
            }
            
            // 3. 可选：apply 表回填 businessId 字段（如有）
            // apply.setBusinessId(businessId);
            // businessApplyMapper.updateById(apply);
        }
        return updated > 0;
    }

    @Override
    public List<BusinessApply> getAppliesByUserId(Long userId) {
        QueryWrapper<BusinessApply> wrapper = new QueryWrapper<>();
        wrapper.eq("applicantId", userId).orderByDesc("applyTime");
        return businessApplyMapper.selectList(wrapper);
    }
}