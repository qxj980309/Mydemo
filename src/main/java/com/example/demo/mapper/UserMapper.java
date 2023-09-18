package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


//@MapperScan
@Mapper
public interface UserMapper extends BaseMapper<User> {


}

