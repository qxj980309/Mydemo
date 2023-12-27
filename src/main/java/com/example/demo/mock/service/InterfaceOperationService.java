package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.InterfaceOperationPO;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceOperationService {
    /*
    * 通过接口查询单条数据
    * @params interfaceId
    * @return 实例对象
    *
    * */
    InterfaceOperationPO selectByInterfaceId(Long interfaceId);
}
