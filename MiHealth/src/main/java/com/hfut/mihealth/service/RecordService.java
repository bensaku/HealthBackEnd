package com.hfut.mihealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hfut.mihealth.DTO.RecordResponse;
import com.hfut.mihealth.entity.Record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 记录;(Records)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2025-4-5
 */
public interface RecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param recordid 主键
     * @return 实例对象
     */
    Record queryById(Integer recordid);

    /**
     * 分页查询
     *
     * @param record 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Record> paginQuery(Record record, long current, long size);
    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    Record insert(Record record);
    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    Record update(Record record);
    /**
     * 通过主键删除数据
     *
     * @param recordid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer recordid);

    /**
     * 通过用户id和日期查询记录
     * @param userId
     * @param date
     * @return
     */
    public Map<String, List<RecordResponse>> getDietRecords(Integer userId, LocalDate date);

    /**
     * 获得一周的饮食摄入统计
     * @param userId
     * @param date
     * @return
     */
    public Map<LocalDate, Map<String, Double>> getWeekRecords(Integer userId, LocalDate date);

    public Map<String,Double> getWeekValue(Integer userId, LocalDate date);
}