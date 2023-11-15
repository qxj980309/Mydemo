package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.InterfaceCasePO;

import java.util.List;

public interface InterfaceCaseService {
    /*
    * 根据接口id查询多案例
    * */
    List<InterfaceCasePO> selectByInterfaceId(Long interfaceId);
}
