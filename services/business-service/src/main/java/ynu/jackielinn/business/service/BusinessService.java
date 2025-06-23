package ynu.jackielinn.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.entity.Business;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BusinessService extends IService<Business> {

    List<Integer> getAllCategories();

    List<BusinessVO> getRecommendBusiness();

    List<BusinessVO> listBusinessByOrderTypeId(Integer orderTypeId);

    BusinessVO listBusinessByBusinessId(Long businessId);

    Double getDeliveryPriceByBusinessId(Long businessId);

    Map<Long, Business> getBusinessInfo(Set<Long> businessIds);
}
