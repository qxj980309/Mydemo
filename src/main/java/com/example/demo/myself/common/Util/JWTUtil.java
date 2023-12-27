package com.example.demo.myself.common.Util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.myself.entity.User;
import com.example.demo.myself.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * jwt工具类
 */
@Component
public class JWTUtil {
    @Resource
    UserService userService;

    @Value("${jwt.expire.offset:}")
    Integer expireOffset;

    /**
     * 创建jwt
     * @param user
     * @return
     */
    public String createToken(User user){
        return JWT.create().withAudience(user.getUserId().toString()) // 设置载荷
//                .withExpiresAt(DateUtil.offsetHour(new Date(), 24)) // 设置签名过期的时间:24小时后
                .withExpiresAt(DateUtil.offsetSecond(new Date(), expireOffset)) // 设置签名过期的时间
                .sign(Algorithm.HMAC256(user.getPassword())); // 签名 Signature
    }


    /**
     * 校验Token，返回用户信息
     * @param token
     * @return
     */
    public User verify(String token){
        DecodedJWT decode = JWT.decode(token);
        String userId = decode.getAudience().get(0);
        User user = userService.getById(userId);
        //获取校验器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        jwtVerifier.verify(token);
        return user;
    }
}
