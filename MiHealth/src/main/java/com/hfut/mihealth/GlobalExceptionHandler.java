package com.hfut.mihealth;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.hfut.mihealth.DTO.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MybatisPlusException.class)
    @ResponseBody
    public ApiResponse handleMyBatisPlusException(MybatisPlusException e, HttpServletRequest request) {
        // 记录日志
        System.out.println("MyBatis-Plus Exception: " + e.getMessage());

        // 返回给客户端的信息
        return ApiResponse.fail(500, "数据库操作失败：" + e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResponse handleBusinessException(BusinessException e, HttpServletRequest request) {
        // 记录日志
        System.out.println("Business Exception: " + e.getMessage());

        // 返回给客户端的信息
        return ApiResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleException(Exception e, HttpServletRequest request) {
        // 记录日志
        System.out.println("Internal Server Error: " + e.getMessage());

        // 返回给客户端的信息
        return ApiResponse.fail(500, "服务器内部错误，请联系管理员");
    }
}

class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}