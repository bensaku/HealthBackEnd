package com.hfut.mihealth.controller;

import com.hfut.mihealth.entity.Food;
import com.hfut.mihealth.interceptor.UserToken;
import com.hfut.mihealth.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食物;(Foods)表控制层
 * @author : wangke
 * @date : 2025-3-25
 */
@Api(tags = "食物对象功能接口")
@RestController
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    /**
     * 通过ID查询单条数据
     *
     * @param foodid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{foodid}")
    public ResponseEntity<Food> queryById(Integer foodid){
        return ResponseEntity.ok(foodService.queryById(foodid));
    }

    /**
     * 分页查询
     *
     * @param food 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<PageImpl<Food>> paginQuery(Food food, PageRequest pageRequest){
        //1.分页参数
        long current = pageRequest.getPageNumber();
        long size = pageRequest.getPageSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Food> pageResult = foodService.paginQuery(food, current,size);
        //3. 分页结果组装
        List<Food> dataList = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageImpl<Food> retPage = new PageImpl<Food>(dataList,pageRequest,total);
        return ResponseEntity.ok(retPage);
    }

    /**
     * 新增数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    @UserToken
    public ResponseEntity<Food> add(@RequestBody Food food){
        return ResponseEntity.ok(foodService.insert(food));
    }

    /**
     * 更新数据
     *
     * @param food 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<Food> edit(Food food){
        return ResponseEntity.ok(foodService.update(food));
    }

    /**
     * 通过主键删除数据
     *
     * @param foodid 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer foodid){
        return ResponseEntity.ok(foodService.deleteById(foodid));
    }
}