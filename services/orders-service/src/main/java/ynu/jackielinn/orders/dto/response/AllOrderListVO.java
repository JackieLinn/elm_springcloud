package ynu.jackielinn.orders.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "所有订单信息对象")
public class AllOrderListVO {
    @Schema(description = "已支付订单信息")
    List<OrderListVO> paidList;
    @Schema(description = "未支付订单信息")
    List<OrderListVO> unpaidList;
}
