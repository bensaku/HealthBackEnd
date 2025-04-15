package com.hfut.mihealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hfut.mihealth.entity.Food;

import java.util.List;
import java.util.Map;

/**
 * 食物;(Foods)表服务接口
 * @author : wangke
 * @date : 2025-3-25
 */
public interface FoodService {

    /**
     * 通过ID查询单条数据
     *
     * @param foodid 主键
     * @return 实例对象
     */
    Food queryById(Integer foodid);

    /**
     * 分页查询
     *
     * @param food 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Food> paginQuery(Food food, long current, long size);
    /**
     * 新增数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    Food insert(Food food);
    /**
     * 更新数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    Food update(Food food);
    /**
     * 通过主键删除数据
     *
     * @param foodid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer foodid);

    /**
     * 查询以食物类型分类的结果
     * @return
     */
    public Map<String, List<Food>> groupByFoodType();
}
