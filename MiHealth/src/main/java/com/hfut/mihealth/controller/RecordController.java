package com.hfut.mihealth.controller;

import java.util.List;

import com.hfut.mihealth.interceptor.UserToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hfut.mihealth.entity.Record;
import com.hfut.mihealth.service.RecordService;

/**
 * 记录;(Records)表控制层
 * @author : wangke
 * @date : 2025-4-2
 */
@Api(tags = "记录对象功能接口")
@RestController
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 通过ID查询单条数据
     *
     * @param recordid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{recordid}")
    public ResponseEntity<Record> queryById(Integer recordid){
        return ResponseEntity.ok(recordService.queryById(recordid));
    }

    /**
     * 分页查询
     *
     * @param record 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<PageImpl<Record>> paginQuery(Record record, PageRequest pageRequest){
        //1.分页参数
        long current = pageRequest.getPageNumber();
        long size = pageRequest.getPageSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Record> pageResult = recordService.paginQuery(record, current,size);
        //3. 分页结果组装
        List<Record> dataList = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageImpl<Record> retPage = new PageImpl<Record>(dataList,pageRequest,total);
        return ResponseEntity.ok(retPage);
    }

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @UserToken
    @PostMapping("/add")
    public ResponseEntity<Record> add(Record record){
        return ResponseEntity.ok(recordService.insert(record));
    }

    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<Record> edit(Record record){
        return ResponseEntity.ok(recordService.update(record));
    }

    /**
     * 通过主键删除数据
     *
     * @param recordid 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer recordid){
        return ResponseEntity.ok(recordService.deleteById(recordid));
    }
}