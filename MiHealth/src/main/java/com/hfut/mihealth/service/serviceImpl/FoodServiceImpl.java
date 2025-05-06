package com.hfut.mihealth.service.serviceImpl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfut.mihealth.entity.Food;
import com.hfut.mihealth.mapper.FoodMapper;
import com.hfut.mihealth.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 食物;(Foods)表服务实现类
 * @author : wangke
 * @date : 2025-3-25
 */
@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodMapper foodMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param foodid 主键
     * @return 实例对象
     */
    public Food queryById(Integer foodid){
        return foodMapper.selectById(foodid);
    }

    /**
     * 分页查询
     *
     * @param food 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<Food> paginQuery(Food food, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Food> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(food.getName())){
            queryWrapper.eq(Food::getName, food.getName());
        }
        if(StrUtil.isNotBlank(food.getFoodtype())){
            queryWrapper.eq(Food::getFoodtype, food.getFoodtype());
        }
        if(StrUtil.isNotBlank(food.getOthernutritionalinfo())){
            queryWrapper.eq(Food::getOthernutritionalinfo, food.getOthernutritionalinfo());
        }
        if(StrUtil.isNotBlank(food.getImageurl())){
            queryWrapper.eq(Food::getImageurl, food.getImageurl());
        }
        //2. 执行分页查询
        Page<Food> pagin = new Page<>(current , size , true);
        IPage<Food> selectResult = foodMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    public Food insert(Food food){
        foodMapper.insert(food);
        return food;
    }

    /**
     * 更新数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    public Food update(Food food){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Food> chainWrapper = new LambdaUpdateChainWrapper<Food>(foodMapper);
        if(StrUtil.isNotBlank(food.getName())){
            chainWrapper.eq(Food::getName, food.getName());
        }
        if(StrUtil.isNotBlank(food.getFoodtype())){
            chainWrapper.eq(Food::getFoodtype, food.getFoodtype());
        }
        if(StrUtil.isNotBlank(food.getOthernutritionalinfo())){
            chainWrapper.eq(Food::getOthernutritionalinfo, food.getOthernutritionalinfo());
        }
        if(StrUtil.isNotBlank(food.getImageurl())){
            chainWrapper.eq(Food::getImageurl, food.getImageurl());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Food::getFoodid, food.getFoodid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(food.getFoodid());
        }else{
            return food;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param foodid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer foodid){
        int total = foodMapper.deleteById(foodid);
        return total > 0;
    }

    @Override
    public Map<String, List<Food>> groupByFoodType() {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        List<Food> foods = foodMapper.selectList(queryWrapper);

        // 使用Java Streams API根据foodtype进行分组
        return foods.stream().collect(Collectors.groupingBy(Food::getFoodtype));
    }

    @Override
    public List<String> getFoodName() {
        return foodMapper.selectName();
    }

    @Override
    public String getFoodNameById(Integer integer) {
        return foodMapper.selectById(integer).getName();
    }
}
