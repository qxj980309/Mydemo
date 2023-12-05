package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.CommonMessagePO;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonMessageService {
    /*
    * 通过id查询单挑数据
    * */
    CommonMessagePO selectById(Long id);
}
