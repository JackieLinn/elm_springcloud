package ynu.jackielinn.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.business.dto.response.BusinessVO;
import ynu.jackielinn.business.entity.Business;

import java.util.List;

public interface BusinessService extends IService<Business> {

    List<Integer> getAllCategories();

    List<BusinessVO> getRecommendBusiness();

    List<BusinessVO> listBusinessByOrderTypeId(Integer orderTypeId);

    BusinessVO listBusinessByBusinessId(Long businessId);

    Double getDeliveryPriceByBusinessId(Long businessId);
}
