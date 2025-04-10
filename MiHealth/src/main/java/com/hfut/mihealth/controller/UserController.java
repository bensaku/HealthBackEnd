package com.hfut.mihealth.controller;

import java.util.List;

import com.hfut.mihealth.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hfut.mihealth.entity.User;
import com.hfut.mihealth.service.UserService;

/**
 * 用户;(Users)表控制层
 * @author : wangke
 * @date : 2025-3-25
 */
@Api(tags = "用户对象功能接口")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param userid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{userid}")
    public ResponseEntity<User> queryById(Integer userid){
        return ResponseEntity.ok(userService.queryById(userid));
    }

    /**
     * 分页查询
     *
     * @param user 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<PageImpl<User>> paginQuery(User user, PageRequest pageRequest){
        //1.分页参数
        long current = pageRequest.getPageNumber();
        long size = pageRequest.getPageSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<User> pageResult = userService.paginQuery(user, current,size);
        //3. 分页结果组装
        List<User> dataList = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageImpl<User> retPage = new PageImpl<User>(dataList,pageRequest,total);
        return ResponseEntity.ok(retPage);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增用户")
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user){
        return ResponseEntity.ok(userService.insert(user));
    }

    /**
     * 新增游客
     *
     *
     */
    @ApiOperation("新增游客")
    @PostMapping("/guest")
    public ResponseEntity<String> addGuest(@RequestBody User users){
        User user = userService.insert(users);
        String token = TokenUtil.generateGuestToken(user.getUserid());
        return ResponseEntity.ok(token);
    }

    /**
     * 更新数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<User> edit(User user){
        return ResponseEntity.ok(userService.update(user));
    }

    /**
     * 通过主键删除数据
     *
     * @param userid 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer userid){
        return ResponseEntity.ok(userService.deleteById(userid));
    }
}