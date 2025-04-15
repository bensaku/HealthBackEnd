package com.hfut.mihealth.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.hfut.mihealth.util.TokenUtil.getGuestIdFromToken;

/**
 * @author ：wangke
 * @date ：2024.4
 * @description：拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //校验是否请求controller
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        UserToken userToken = handlerMethod.getMethodAnnotation(UserToken.class);
        if (userToken == null) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            setResponse(response); // 假设这是设置响应为未授权的方法
            return false;
        }
        // 从 Authorization 头部中提取 token
        String token = authHeader.substring(7); // 移除 "Bearer " 前缀

        if (token == null || token.isEmpty()) {
            setResponse(response);
            return false;
        }

        // 这里可以根据需要进一步解析与校验Token中的信息，比如获取用户ID等
        Integer userId = getGuestIdFromToken(token);

        if (userId == null) {
            setResponse(response);
            return false;
        }

        // 可以将userId存储在request中供后续使用
        request.setAttribute("userId", userId);
        return true;

//        Parameter[] parameters = handlerMethod.getMethod().getParameters();
//        for (Parameter parameter : parameters) {
//            if (parameter.getName().equals("token")) {
//                String token = request.getHeader("token");
//                if (token == null) {
//                    setResponse(response);
//                    return false;
//                }
////                 这里可以根据需要进一步解析与校验Token中的信息，比如获取用户ID等
//                Integer userId = getGuestIdFromToken(token);
////                 可以将userId存储在request中供后续使用
//                request.setAttribute("userId", userId);
//                return true;
//            }
//        }
//        setResponse(response);
//        return false;
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.reset();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Invalid or missing token");
    }
}