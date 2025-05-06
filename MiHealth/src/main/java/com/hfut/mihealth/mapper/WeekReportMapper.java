package com.hfut.mihealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hfut.mihealth.entity.WeekReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.Date;

@Mapper
public interface WeekReportMapper extends BaseMapper<WeekReport> {
    @Select("SELECT * FROM week_report WHERE user_id = #{userId} AND week_start_date = #{weekStartDate}")
    WeekReport selectByUserIdAndWeekStartDate(@Param("userId") Integer userId, @Param("weekStartDate") Date weekStartDate);

    @Update("UPDATE week_report SET status = #{status}, error_message = #{errorMessage} WHERE id = #{id}")
    void updateStatusById(@Param("id") Long id, @Param("status") String status, @Param("errorMessage") String errorMessage);
}
