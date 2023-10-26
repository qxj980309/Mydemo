package com.example.demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mock.entity.po.ProjectPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProjectMapper extends BaseMapper<ProjectPo> {

    @Select("select id,name from td_project where delete_flag = 0 ")
    List<ProjectPo> getAllProjectId();

    
}
