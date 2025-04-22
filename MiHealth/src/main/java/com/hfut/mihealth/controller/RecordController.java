package com.hfut.mihealth.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hfut.mihealth.DTO.RecordAndImageResponse;
import com.hfut.mihealth.DTO.RecordResponse;
import com.hfut.mihealth.entity.Image;
import com.hfut.mihealth.interceptor.UserToken;
import com.hfut.mihealth.service.ImageService;
import com.hfut.mihealth.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ImageService imageService;

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

    @ApiOperation("批量新增数据")
    @UserToken
    @PostMapping("/addBatch")
    public ResponseEntity<Boolean> addBatch(@RequestBody List<Record> records, @RequestHeader(value = "Authorization") String token) {
        List<Record> successfulInserts = new ArrayList<>();
        if (token != null && token.startsWith("Bearer ")) {
            // 移除 "Bearer " 前缀
            token = token.substring(7);
        }
        Integer userId = TokenUtil.getGuestIdFromToken(token);

        for (Record record : records) {
            record.setUserid(userId);
            // 调用现有的 add 方法
            ResponseEntity<Record> response = add(record);
            if (response.getStatusCode().is2xxSuccessful()) {
                successfulInserts.add(response.getBody());
            }
        }
        return ResponseEntity.ok(true);
    }

    /**
     * 通过日期查询记录
     * @param date
     * @return
     * @throws ParseException
     */
    @GetMapping("/diet")
    public ResponseEntity<?> getDietRecords(
            @RequestParam String date,@RequestHeader(value = "Authorization") String token) throws ParseException {
        if (token != null && token.startsWith("Bearer ")) {
            // 移除 "Bearer " 前缀
            token = token.substring(7);
        }
        Integer userId = TokenUtil.getGuestIdFromToken(token);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        Map<String, List<RecordResponse>> dietRecords = recordService.getDietRecords(userId, localDate);
        RecordAndImageResponse recordAndImageResponse = new RecordAndImageResponse();
        recordAndImageResponse.setDietRecords(dietRecords);
        recordAndImageResponse.setImageList(imageService.getAllImage(userId,localDate));

        return ResponseEntity.ok(recordAndImageResponse);
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