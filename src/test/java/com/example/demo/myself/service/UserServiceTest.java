package com.example.demo.myself.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.myself.common.result.Result;
import com.example.demo.myself.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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