package com.example.demo.mock.mapper;

import com.example.demo.mock.entity.vo.ProVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface InterfaceCaseMapper {

    @Select("Select id from td_interfaceCase where interface_id = #{id} group by id")
    List<Long> alIdByInterfaceId(Long id);

    @Select("SELECT a.name,count(c.interface_id) as count" +
            "  FROM td_project a LEFT JOIN td_interface b ON a.id = b.project_id LEFT JOIN td_interfacecase c ON b.id = c.interface_id\n" +
            "  GROUP BY a.name")
    List<ProVO> alIdByInterfaceId1();

}
