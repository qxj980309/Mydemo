package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.result.Result;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userServiceIml;
    @Test
    void add() {

    }

    @Test
    void findUser() {
    }

    @Test
    void finaAll() {
        Result<User> find = userServiceIml.finaAll();
//        String u = JSONObject.toJSONString(find);
        System.out.println("userResult = " + JSONObject.toJSONString(find) );
    }
}