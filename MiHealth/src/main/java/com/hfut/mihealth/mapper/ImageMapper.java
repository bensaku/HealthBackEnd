package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfut.mihealth.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
}