package ynu.jackielinn.address.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielinn.address.dto.request.DeliveryAddressSaveRO;
import ynu.jackielinn.address.dto.request.DeliveryAddressUpdateRO;
import ynu.jackielinn.address.dto.response.DeliveryAddressVO;
import ynu.jackielinn.address.entity.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService extends IService<DeliveryAddress> {

    List<DeliveryAddressVO> listDeliveryAddressByUserId(Long userId);

    Integer saveDeliveryAddress(DeliveryAddressSaveRO ro);

    Integer updateDeliveryAddress(DeliveryAddressUpdateRO ro);

    Integer deleteDeliveryAddress(Long daId);
}
