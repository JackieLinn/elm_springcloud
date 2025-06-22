package ynu.jackielinn.orders.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.jackielinn.orders.entity.Orders;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
