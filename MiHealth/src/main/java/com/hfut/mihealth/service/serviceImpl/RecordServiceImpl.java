package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.DTO.RecordResponse;
import com.hfut.mihealth.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.hfut.mihealth.entity.Record;
import com.hfut.mihealth.mapper.RecordMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 记录;(Records)表服务实现类
 *
 * @author : http://www.chiner.pro
 * @date : 2025-4-5
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param recordid 主键
     * @return 实例对象
     */
    public Record queryById(Integer recordid) {
        return recordMapper.selectById(recordid);
    }

    /**
     * 分页查询
     *
     * @param record  筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    public Page<Record> paginQuery(Record record, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        Page<Record> pagin = new Page<>(current, size, true);
        IPage<Record> selectResult = recordMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    public Record insert(Record record) {
        recordMapper.insert(record);
        return record;
    }

    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    public Record update(Record record) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Record> chainWrapper = new LambdaUpdateChainWrapper<Record>(recordMapper);
        //2. 设置主键，并更新
        chainWrapper.set(Record::getRecordid, record.getRecordid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(record.getRecordid());
        } else {
            return record;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param recordid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer recordid) {
        int total = recordMapper.deleteById(recordid);
        return total > 0;
    }

    @Override
    public Map<String, List<RecordResponse>> getDietRecords(Integer userId, LocalDate date) {
        List<Map<String, Object>> records = recordMapper.selectRecordsByDateAndUserId(userId, date);

        // 将 Map<String, Object> 转换为 RecordResponse 对象
        List<RecordResponse> recordResponses = records.stream().map(record -> new RecordResponse(
                ((Number) record.get("UserID")).intValue(),
                ((Number) record.get("FoodID")).intValue(),
                ((Number) record.get("RecordID")).intValue(),
                ((Number) record.get("Amount")).intValue(),
                (String) record.get("name"),
                convertToIntegerWithCheck(record.get("calories")), // 使用专门的方法来处理可能的浮点数
                convertBigDecimalToDouble(record.get("protein")),
                convertBigDecimalToDouble(record.get("carbs")),
                convertBigDecimalToDouble(record.get("fat")),
                (String) record.get("Meals"),
                (String) record.get("imageURL"),
                (Date) record.get("date")
        )).toList();

        // 根据 "Meals" 字段对 RecordResponse 对象进行分组
        return recordResponses.stream().collect(Collectors.groupingBy(RecordResponse::getMeals));
    }
    private double convertBigDecimalToDouble(Object value) {
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new IllegalArgumentException("Unsupported type for conversion to double: " + value.getClass());
    }

    private int convertToIntegerWithCheck(Object value) {
        if (value instanceof BigDecimal) {
            BigDecimal bigDecimalValue = (BigDecimal) value;
            // 检查是否有小数部分
            if (bigDecimalValue.scale() > 0) {
                // 如果有小数部分，可以选择四舍五入或其他方式处理
                // 这里使用 HALF_UP 表示四舍五入
                return bigDecimalValue.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
            } else {
                // 如果没有小数部分，直接转换为 int
                return bigDecimalValue.intValue();
            }
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new IllegalArgumentException("Unsupported type for conversion to integer: " + value.getClass());
    }
}