package ynu.jackielinn.food.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.jackielinn.food.dto.response.FoodVO;
import ynu.jackielinn.food.entity.Food;
import ynu.jackielinn.food.mapper.FoodMapper;
import ynu.jackielinn.food.service.FoodService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    /**
     * 商家/管理端根据商家ID查询所有食品列表
     *
     * @param businessId 商家ID，不能为空
     * @return 食品列表，列表中的元素是FoodVO对象
     * @throws IllegalArgumentException 如果businessId为空，则抛出此异常
     */
    @Override
    public List<FoodVO> listAllFoodByBusinessId(Long businessId) {
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
     * 客户端根据商家ID查询所有上架食品列表
     *
     * @param businessId 商家ID，不能为空
     * @return 食品列表，列表中的元素是FoodVO对象
     * @throws IllegalArgumentException 如果businessId为空，则抛出此异常
     */
    @Override
    public List<FoodVO> listOnShelfFoodByBusinessId(Long businessId) {
        if (businessId == null) {
            throw new IllegalArgumentException("Business ID cannot be null");
        }
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("businessId", businessId)
                .eq("foodStatus", 1)
                .eq("isDeleted", 0);
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

    /**
     * 根据食品ID集合获取食品信息
     * 此方法使用MyBatis-Plus的QueryWrapper来构建查询条件，使用foodIds参数进行in查询，
     * 然后将查询结果转换为Map，便于后续操作
     *
     * @param foodIds 食品ID集合，用于查询食品信息
     * @return 返回一个Map，键为食品ID，值为Food对象
     */
    @Override
    public Map<Long, Food> getFoodInfo(Set<Long> foodIds) {
        QueryWrapper<Food> foodWrapper = new QueryWrapper<>();
        foodWrapper.in("foodId", foodIds);
        List<Food> foods = baseMapper.selectList(foodWrapper);
        return foods.stream()
                .collect(Collectors.toMap(Food::getFoodId, f -> f));
    }

    @Override
    public boolean addFood(Food food) {
        food.setFoodStatus(0); // 默认下架
        food.setIsDeleted(0); // 默认未删除
        return this.save(food);
    }

    @Override
    public boolean updateFood(Food food) {
        return this.updateById(food);
    }

    @Override
    public boolean deleteFood(Long foodId) {
        Food food = this.getById(foodId);
        if (food == null || food.getIsDeleted() != 0) return false;
        food.setIsDeleted(1);
        return this.updateById(food);
    }

    @Override
    public boolean updateStatus(Long foodId, Integer foodStatus) {
        Food food = this.getById(foodId);
        if (food == null || food.getIsDeleted() != 0) return false;
        food.setFoodStatus(foodStatus);
        return this.updateById(food);
    }


}
