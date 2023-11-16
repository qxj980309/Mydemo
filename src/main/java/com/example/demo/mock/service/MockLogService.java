package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.InterfacePO;
import org.springframework.stereotype.Repository;

@Repository
public interface MockLogService {
    /*
    * 异步-存数据至log表
    * */
    void saveMockLog(InterfacePO interfacePO,String traceId);
}
