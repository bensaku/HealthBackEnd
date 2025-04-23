package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfut.mihealth.entity.UserFoodRating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserFoodRatingMapper extends BaseMapper<UserFoodRating> {
    @Select("SELECT COUNT(*) FROM UserFoodRating WHERE user_id = #{userId} AND food_id = #{foodId}")
    int checkRecordExists(@Param("userId") Integer userId, @Param("foodId") Integer foodId);

    @Update("INSERT INTO UserFoodRating(user_id, food_id, count, last_selected_date) " +
            "VALUES(#{userId}, #{foodId}, 1, NOW()) ON DUPLICATE KEY UPDATE count = count + 1, last_selected_date = NOW()")
    void insertOrUpdate(@Param("userId") Integer userId, @Param("foodId") Integer foodId);
}
