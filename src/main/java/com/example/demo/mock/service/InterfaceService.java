package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.InterfacePO;

import java.util.List;

public interface InterfaceService {
    /*
    * 通过url 和 txCode 匹配接口
    *
    * @param  projectId  工程id
    * @param  txCode  接口号
    * @param  url   url
    * @return 对象
    * */
    InterfacePO selectInterface(Long projectId,String txCode,String url);

    /*
    * 条件查询多条数据
    *
    * @interfacePO 实例对象
    * @return 对象列表
    * */
    List<InterfacePO> selectList(InterfacePO interfacePO);
}
