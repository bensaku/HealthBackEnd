package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfut.mihealth.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    // 可以根据需要在此处定义更多的SQL语句方法
}