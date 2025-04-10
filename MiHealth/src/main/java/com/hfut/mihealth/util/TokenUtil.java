package com.hfut.mihealth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {

    // 假设使用固定的密钥来签名JWT
    private static final String SECRET = "your-secure-secret-key"; // 使用一个强密钥

    /**
     * 为游客生成Token
     *
     * @param guestId 游客的唯一标识符
     * @return 生成的Token
     */
    public static String generateGuestToken(Integer guestId) {
        return JWT.create()
                .withClaim("guestId", guestId)
                .sign(Algorithm.HMAC256(SECRET)); // 使用HMAC256算法和密钥进行签名
    }

    /**
     * 解析并验证给定的JWT Token。
     *
     * @param token 需要解析的JWT Token字符串
     * @return 提取出来的guestId
     * @throws JWTVerificationException 如果Token无效或无法验证
     */
    public static Integer getGuestIdFromToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .build(); // 创建一个验证器实例

        DecodedJWT jwt = verifier.verify(token); // 验证Token并解码
        return jwt.getClaim("guestId").asInt(); // 获取guestId声明并转换为Integer
    }

}
