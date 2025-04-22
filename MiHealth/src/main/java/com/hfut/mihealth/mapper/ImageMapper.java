package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfut.mihealth.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    // 可以根据需要在此处定义更多的SQL语句方法
    @Insert("INSERT INTO image(user_id, date, timestamp, amount, completed, food_name) " +
            "VALUES(#{userId}, #{date}, #{timestamp}, #{amount}, #{completed}, #{foodName})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId", keyColumn = "image_id")
    void insertImage(@Param("image") Image image);
}