package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.result.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserTest {
    @Resource
    private UserService userServiceIml;

    @Test
    public void main() {
        User user = new User();
        user.setUserid(8);
        user.setUsername("二丫");
        user.setPassword("123456");
//        userServiceIml.add(user);
        Result<User> find = userServiceIml.finaAll();
        String u = JSONObject.toJSONString(find);
        System.out.println("userResult = " + u );
//        Result<User> add = userServiceIml.add(user);
//        String u1 = JSONObject.toJSONString(add);
//        System.out.println("userResult = " + u1);
    }
}
