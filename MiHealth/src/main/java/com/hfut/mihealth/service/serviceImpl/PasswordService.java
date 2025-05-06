package com.hfut.mihealth.service.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 对密码进行哈希处理
     *
     * @param password 明文密码
     * @return 哈希后的密码
     */
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 验证提供的原始密码与存储的哈希密码是否匹配
     *
     * @param rawPassword     用户输入的原始密码
     * @param encodedPassword 数据库中存储的哈希密码
     * @return 是否匹配
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
