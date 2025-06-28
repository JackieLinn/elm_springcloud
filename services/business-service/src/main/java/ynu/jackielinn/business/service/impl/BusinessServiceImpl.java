package ynu.jackielinn.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.entity.Business;
import ynu.jackielinn.business.entity.BusinessEsDoc;
import ynu.jackielinn.business.mapper.BusinessEsRepository;
import ynu.jackielinn.business.mapper.BusinessMapper;
import ynu.jackielinn.business.service.BusinessService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Resource
    private BusinessEsRepository businessEsRepository;

    /**
     * 获取所有的点餐分类
     *
     * @return 一个包含所有订单类型ID的列表，列表中的元素是唯一且有序的
     */
    @Override
    public List<Integer> getAllCategories() {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("orderTypeId");
        List<Object> orderTypeIds = baseMapper.selectObjs(queryWrapper);
        return orderTypeIds.stream()
                .map(obj -> (Integer) obj).distinct().sorted(Integer::compareTo).collect(Collectors.toList());
    }

    /**
     * 获取推荐商家列表
     *
     * @return 一个包含推荐商家信息的列表，列表中的元素是按照商家ID升序排列的前6个商家
     */
    @Override
    public List<BusinessVO> getRecommendBusiness() {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("businessId")
                .last("LIMIT 6");
        List<Business> businesses = baseMapper.selectList(queryWrapper);
        return businesses.stream()
                .map(b -> b.asViewObject(BusinessVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据类型ID查询商家列表
     *
     * @param orderTypeId 订单类型ID，为0时查询所有商家
     * @return 商家列表，列表中的元素是BusinessVO对象
     */
    @Override
    public List<BusinessVO> listBusinessByOrderTypeId(Integer orderTypeId) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        if (orderTypeId != 0) {
            queryWrapper.eq("orderTypeId", orderTypeId);
        }
        List<Business> businesses = baseMapper.selectList(queryWrapper);
        return businesses.stream()
                .map(b -> b.asViewObject(BusinessVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据类型ID查询商家列表
     *
     * @param businessId 商家ID
     * @return 商家BusinessVO对象
     */
    @Override
    public BusinessVO listBusinessByBusinessId(Long businessId) {
        Business business = baseMapper.selectById(businessId);
        if (business == null) {
            return null;
        }
        return business.asViewObject(BusinessVO.class);
    }

    /**
     * 根据商家ID获取配送价格
     *
     * @param businessId 商家ID，用于查询特定商家的配送价格
     * @return 商家的配送价格如果找不到对应的商家，则返回0.0
     */
    @Override
    public Double getDeliveryPriceByBusinessId(Long businessId) {
        QueryWrapper<Business> wrapper = new QueryWrapper<>();
        wrapper.eq("businessId", businessId);
        Business business = baseMapper.selectOne(wrapper);
        if (business != null) {
            return business.getDeliveryPrice();
        } else {
            return 0.0;
        }
    }

    /**
     * 根据业务ID列表获取业务信息映射
     * 该方法使用MyBatis-Plus的QueryWrapper来构造查询条件，通过businessIds参数进行查询，
     * 并将查询结果转换为一个映射，其中键为业务ID，值为业务对象
     *
     * @param businessIds 业务ID列表，用于查询数据库中的业务信息
     * @return 返回一个映射，键为业务ID，值为对应的业务对象 如果查询不到任何记录，则返回空映射
     */
    @Override
    public Map<Long, Business> getBusinessInfo(Set<Long> businessIds) {
        QueryWrapper<Business> businessWrapper = new QueryWrapper<>();
        businessWrapper.in("businessId", businessIds);
        List<Business> businesses = baseMapper.selectList(businessWrapper);
        return businesses.stream()
                .collect(Collectors.toMap(Business::getBusinessId, b -> b));
    }

    /**
     * 根据搜索词获取相关商家的列表信息
     *
     * @param keyword 搜索关键词，用于查询ElasticSearch中的商家信息
     * @return 返回商家列表信息 如果查询不到任何记录，则返回空列表
     */
    @Override
    public List<BusinessEsDoc> searchByName(String keyword) {
        return businessEsRepository.findByBusinessNameContaining(keyword);
    }

    @Override
    public boolean updateBusinessInfo(Business business) {
        // 只允许更新部分字段，防止误操作
        Business dbBusiness = baseMapper.selectById(business.getBusinessId());
        if (dbBusiness == null) return false;
        dbBusiness.setBusinessName(business.getBusinessName());
        dbBusiness.setBusinessAddress(business.getBusinessAddress());
        dbBusiness.setBusinessExplain(business.getBusinessExplain());
        dbBusiness.setBusinessImg(business.getBusinessImg());
        dbBusiness.setOrderTypeId(business.getOrderTypeId());
        dbBusiness.setStartPrice(business.getStartPrice());
        dbBusiness.setDeliveryPrice(business.getDeliveryPrice());
        dbBusiness.setRemarks(business.getRemarks());
        return baseMapper.updateById(dbBusiness) > 0;
    }

    /**
     * 修改商家状态（禁用/启用）
     * 该方法根据商家ID修改其状态（status），1为正常，0为禁用
     * @param businessId 商家ID
     * @param status 商家状态（1: 正常, 0: 禁用）
     * @return 操作结果，成功返回true，失败返回false
     */
    @Override
    public boolean updateBusinessStatus(Long businessId, int status) {
        Business business = baseMapper.selectById(businessId);
        if (business == null) return false;
        business.setStatus(status);
        return baseMapper.updateById(business) > 0;
    }
}
