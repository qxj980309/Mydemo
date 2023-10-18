package com.example.demo.mock.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InterfaceMapper {

    @Select("Select id from td_interface where projectId = #{id} group by id")
    List<Long> alIdByProId(Long id);


}
