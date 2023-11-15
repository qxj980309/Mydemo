package com.example.demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mock.entity.po.DynamicMsgPO;
import com.example.demo.mock.entity.po.InterfaceCasePO;
import com.example.demo.mock.entity.vo.ProVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface InterfaceCaseMapper extends BaseMapper<InterfaceCasePO> {

    void  dynamicMsgUpdate(@Param("id")Long id, @Param("dynamicMessage")List<DynamicMsgPO> dynamicMessage);

    @Select("Select id from td_interfaceCase where interface_id = #{id} and delete_flag = 0 group by id")
    List<Long> alIdByInterfaceId(Long id);

    @Select("SELECT a.id,a.name,count(c.interface_id) as count\n" +
            " FROM td_project a LEFT JOIN td_interface b ON a.id = b.project_id AND a.delete_flag = 0 \n" +
            " LEFT JOIN td_interfacecase c ON b.id = c.interface_id AND b.delete_flag = 0 AND c.delete_flag = 0\n" +
            " GROUP BY a.id,a.name")
    List<ProVO> alIdByInterfaceId1();

}
