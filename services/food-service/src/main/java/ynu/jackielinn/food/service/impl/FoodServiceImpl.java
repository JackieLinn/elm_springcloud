package ynu.jackielinn.food.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.entity.Food;
import ynu.jackielinn.food.mapper.FoodMapper;
import ynu.jackielinn.food.service.FoodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

    /**
     * 根据商家ID查询食品列表
     *
     * @param businessId 商家ID，不能为空
     * @return 食品列表，列表中的元素是FoodVO对象
     * @throws IllegalArgumentException 如果businessId为空，则抛出此异常
     */
    @Override
    public List<FoodVO> listFoodByBusinessId(Long businessId) {
        if (businessId == null) {
            throw new IllegalArgumentException("Business ID cannot be null");
        }
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("businessId", businessId);
        List<Food> foods = baseMapper.selectList(queryWrapper);
        return foods.stream()
                .map(f -> f.asViewObject(FoodVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据食物ID获取食物信息
     * 此方法通过接收一个食物ID，从数据库中查询对应的食物信息
     * 如果找到对应的食物信息，则将其转换为FoodVO对象并返回；
     * 如果没有找到，则返回null
     *
     * @param foodId 食物ID，用于查询数据库中的食物记录
     * @return FoodVO 如果找到对应的食物信息，则返回FoodVO对象；否则返回null
     */
    @Override
    public FoodVO getFoodByFoodId(Long foodId) {
        Food food = baseMapper.selectById(foodId);
        if (food == null) return null;
        else return food.asViewObject(FoodVO.class);
    }
}
