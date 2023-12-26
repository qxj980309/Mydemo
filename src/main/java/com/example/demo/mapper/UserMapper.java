package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//@MapperScan
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*
    * 通过用户名查找用户信息
    * */
    @Select("Select * from user where user_name = #{userName} and password = #{password}")
    User getByUsername(String username,String password);
}

