package com.example.demo.mock.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InterfaceCaseMapper {

    @Select("Select id from td_interfaceCase where interfaceId = #{id} group by id")
    List<Long> alIdByInterfaceId(Long id);

}
