package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hfut.mihealth.entity.Record;

/**
 * 记录;(Records)表数据库访问层
 * @author : wangke
 * @date : 2025-4-2
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record>{
    /**
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Record> selectByPage(IPage<Record> page , @Param(Constants.WRAPPER) Wrapper<Record> wrapper);
}