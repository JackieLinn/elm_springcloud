package ynu.jackielinn.address.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielinn.address.dto.request.DeliveryAddressSaveRO;
import ynu.jackielinn.address.dto.request.DeliveryAddressUpdateRO;
import ynu.jackielinn.address.dto.response.DeliveryAddressVO;
import ynu.jackielinn.address.service.DeliveryAddressService;
import ynu.jackielinn.common.entity.RestBean;

import java.util.List;

@RestController
@RequestMapping("/api/deliveryAddress")
@Tag(name = "送餐地址相关接口", description = "与送餐地址相关的操作接口")
public class DeliveryAddressController {

    @Resource
    DeliveryAddressService deliveryAddressService;

    @Operation(summary = "获取用户所有地址信息", description = "获取用户所有地址信息")
    @GetMapping("/get-all-address")
    public RestBean<List<DeliveryAddressVO>> getAllAddress(Long userId) {
        return RestBean.success(deliveryAddressService.listDeliveryAddressByUserId(userId));
    }

    @Operation(summary = "保存地址信息", description = "保存地址信息")
    @PostMapping("/save-address")
    public RestBean<Integer> saveAddress(@RequestBody DeliveryAddressSaveRO ro) {
        return RestBean.success(deliveryAddressService.saveDeliveryAddress(ro));
    }

    @Operation(summary = "更新地址信息", description = "更新地址信息")
    @PostMapping("/update-address")
    public RestBean<Integer> updateAddress(@RequestBody DeliveryAddressUpdateRO ro) {
        return RestBean.success(deliveryAddressService.updateDeliveryAddress(ro));
    }

    @Operation(summary = "移除地址信息", description = "移除地址信息")
    @PostMapping("/remove-address")
    public RestBean<Integer> removeAddress(Long daId) {
        return RestBean.success(deliveryAddressService.deleteDeliveryAddress(daId));
    }
}
