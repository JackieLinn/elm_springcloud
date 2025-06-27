package ynu.jackielinn.orders.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class OrderDetailVO {
    private Long orderId;
    private String orderDate;
    private Double orderTotal;
    private Integer orderState;
    private String businessName;
    private Double deliveryPrice;
    private List<OrdersFoodVO> foodList;
    // 可扩展：用户昵称、手机号等
}
