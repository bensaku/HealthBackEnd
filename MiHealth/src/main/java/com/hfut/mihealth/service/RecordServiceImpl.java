package com.hfut.mihealth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.hfut.mihealth.entity.Record;
import com.hfut.mihealth.mapper.RecordMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 记录;(Records)表服务实现类
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
    public Record queryById(Integer recordid){
        return recordMapper.selectById(recordid);
    }

    /**
     * 分页查询
     *
     * @param record 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<Record> paginQuery(Record record, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        //2. 执行分页查询
        Page<Record> pagin = new Page<>(current , size , true);
        IPage<Record> selectResult = recordMapper.selectByPage(pagin , queryWrapper);
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
    public Record insert(Record record){
        recordMapper.insert(record);
        return record;
    }

    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    public Record update(Record record){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Record> chainWrapper = new LambdaUpdateChainWrapper<Record>(recordMapper);
        //2. 设置主键，并更新
        chainWrapper.set(Record::getRecordid, record.getRecordid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(record.getRecordid());
        }else{
            return record;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param recordid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer recordid){
        int total = recordMapper.deleteById(recordid);
        return total > 0;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getDietRecords(Integer userId, LocalDate date) {
        List<Map<String, Object>> records = recordMapper.selectRecordsByDateAndUserId(userId, date);

        return records.stream().collect(Collectors.groupingBy(
                record -> ((String) record.get("Meals"))));
    }
}