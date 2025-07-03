package ynu.jackielinn.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.entity.Business;
import ynu.jackielinn.business.entity.BusinessEsDoc;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BusinessService extends IService<Business> {

    List<Integer> getAllCategories();

    List<BusinessVO> getRecommendBusiness();

    List<BusinessVO> listBusinessByOrderTypeId(Integer orderTypeId);

    BusinessVO listBusinessByBusinessId(Long businessId);

    Business listBusinessById(Long businessId);

    Double getDeliveryPriceByBusinessId(Long businessId);

    Map<Long, Business> getBusinessInfo(Set<Long> businessIds);

    List<BusinessEsDoc> searchByName(String keyword);

    /**
     * 修改商家信息
     */
    boolean updateBusinessInfo(Business business);

    /**
     * 修改商家状态（禁用/启用）
     * @param businessId 商家ID
     * @param status 商家状态（1: 正常, 0: 禁用）
     * @return 操作结果，成功返回true，失败返回false
     */
    boolean updateBusinessStatus(Long businessId, int status);

    /**
     * 管理员分页查找所有商家
     */
    IPage<Business> adminListBusinesses(int pageNum, int pageSize, Integer status, String keyword);
}
