package com.example.demo.service.iml;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.result.ResultEnum;
import com.example.demo.common.result.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceIml extends ServiceImpl<UserMapper, User> implements UserService{
    @Resource
    private UserMapper userMapper;


    @Override
    public Result add(User user) {
        User uid = userMapper.selectById(user.getUserid());
        if(uid != null){
            return Result.error(500,"该用户已存在");
        }
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setCreatetime(new java.util.Date());
        userMapper.insert(user);
        return Result.ok(200,"成功",user);
    }

    @Override
    public Result findUser(int userId) {
        User user = userMapper.selectById(userId);
        if (user == null){
            return Result.error();
        }
        return  Result.ok(200,"成功",user);
    }




//    @Override
//    public List<User> list() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
//        List<User> user = userMapper.selectList(queryWrapper);
//        return user;
//    }
    @Override
    public Result<User> finaAll() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        List<User> user = userMapper.selectList(queryWrapper);
        if (user == null){
            return Result.error();
        }
        String s = JSONObject.toJSONString(user);
        System.out.println("s = " + s);
        return Result.error(200,"成功",user);
    }
}
