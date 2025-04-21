package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.hfut.mihealth.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 食物;(Foods)表数据库访问层
 * @author : wangke
 * @date : 2025-3-25
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food>{
    /**
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Food> selectByPage(IPage<Food> page , @Param(Constants.WRAPPER) Wrapper<Food> wrapper);

    @Select("SELECT Name FROM Food")
    List<String> selectName();
}