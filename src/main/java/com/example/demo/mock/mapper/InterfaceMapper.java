package com.example.demo.mock.mapper;

import com.example.demo.mock.entity.vo.ProVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface InterfaceMapper {

    @Select("Select id from td_interface where project_id = #{id} group by id")
    List<Long> alIdByProId(Long id);

    @Select("SELECT a.name,count(b.project_id) as count FROM td_project a LEFT JOIN td_interface b on a.id = b.project_id GROUP BY a.id ,a.name")
    List<ProVO> alIdByProId1();

}
