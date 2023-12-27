package com.example.demo.mock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.myself.common.result.Result;
import com.example.demo.mock.entity.po.DynamicMsgPO;
import com.example.demo.mock.entity.po.InterfaceCasePO;
import com.example.demo.mock.entity.vo.InterfaceCaseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 接口案例(tb_interface_case)服务接口
* */
@Repository
public interface InterfaceCaseService {
    /*
    * 通过ID查询单条数据
    * @param id 主键
    * @return 实例对象
    * */
    InterfaceCasePO selectById(Long id);

    /*
    * 条件查询多条数据
    *
    *  @param interfaceCasePO 实例对象
    * @return 对象列表
    * */
    List<InterfaceCasePO> selectList(InterfaceCasePO interfaceCasePO);

    /*
    * 根据接口id查询多案例
    *
    * @param interfaceId 接id
    * @return 对象列表
    * */
    List<InterfaceCasePO> selectByInterfaceId(Long interfaceId);

    /*
    * 分页查询多条数据
    * */
    IPage<InterfaceCaseVO> selectPage(InterfaceCaseVO interfaceCaseVO,Integer pageIndex,Integer pageSize);

    /*
    * 新增数据
    *
    * @param interfaceCaseVO  对象
    * @return 实例对象
    * */
    Result<?> insert(InterfaceCaseVO interfaceCaseVO);

    /*
    * 修改数据
    *
    *  @param interfaceCaseVO  对象
    * */
    Result<?> updateById(InterfaceCaseVO interfaceCaseVO);

    /*
    * 通过主健删除
    *
    *  @param id
    * */
    Result<?> deleteById(Long id);

    /*
    * 更新值为uLL的ruleId
    *
    * @param (ruleId，id) 规则Id,主键
    * */
    void ruleIdUpdateNull(Long ruleId, Long id);

    /*
    * *更新 interfaceCaseP0
    *
    * @param interfaceCaseP0 实例对象
    * */
    Integer updateCasePoById(InterfaceCasePO interfaceCasePO);

    /*
    * 动态报文更新存储
    *
    * @param id 案例表id
    * @param dynamicMessage 动态报文信息
    * */
    void dynamicMsgUpdate(Long id , List<DynamicMsgPO> dynamicMsgPOList);
}
