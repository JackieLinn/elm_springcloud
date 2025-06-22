package ynu.jackielinn.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.address.dto.request.DeliveryAddressSaveRO;
import ynu.jackielinn.address.dto.request.DeliveryAddressUpdateRO;
import ynu.jackielinn.address.dto.response.DeliveryAddressVO;
import ynu.jackielinn.address.entity.DeliveryAddress;
import ynu.jackielinn.address.mapper.DeliveryAddressMapper;
import ynu.jackielinn.address.service.DeliveryAddressService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryAddressServiceImpl extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress>
        implements DeliveryAddressService {

    /**
     * 根据用户ID查询并返回该用户的所有配送地址
     *
     * @param userId 用户ID
     * @return 该用户的所有配送地址列表
     */
    @Override
    public List<DeliveryAddressVO> listDeliveryAddressByUserId(Long userId) {
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<DeliveryAddress> deliveryAddresses = baseMapper.selectList(queryWrapper);
        return deliveryAddresses.stream().
                map(da -> da.asViewObject(DeliveryAddressVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 保存配送地址
     *
     * @param ro 包含配送地址信息的对象
     * @return 插入操作影响的行数
     */
    @Override
    public Integer saveDeliveryAddress(DeliveryAddressSaveRO ro) {
        DeliveryAddress deliveryAddress = new DeliveryAddress(null, ro.getContactName(), ro.getContactSex(),
                ro.getContactTel(), ro.getAddress(), ro.getUserId());
        return this.baseMapper.insert(deliveryAddress);
    }

    /**
     * 更新配送地址
     *
     * @param ro 包含配送地址信息的对象
     * @return 更新操作影响的行数
     */
    @Override
    public Integer updateDeliveryAddress(DeliveryAddressUpdateRO ro) {
        UpdateWrapper<DeliveryAddress> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("daId", ro.getDaId());
        updateWrapper.set("contactName", ro.getContactName());
        updateWrapper.set("contactSex", ro.getContactSex());
        updateWrapper.set("contactTel", ro.getContactTel());
        updateWrapper.set("address", ro.getAddress());
        updateWrapper.set("userId", ro.getUserId());
        return this.baseMapper.update(null, updateWrapper);
    }

    /**
     * 根据配送地址ID删除该配送地址
     *
     * @param daId 配送地址ID
     * @return 删除操作影响的行数
     */
    @Override
    public Integer deleteDeliveryAddress(Long daId) {
        return this.baseMapper.deleteById(daId);
    }
}
