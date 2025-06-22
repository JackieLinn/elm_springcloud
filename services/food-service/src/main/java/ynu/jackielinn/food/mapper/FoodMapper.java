package ynu.jackielinn.food.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.jackielinn.food.entity.Food;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
