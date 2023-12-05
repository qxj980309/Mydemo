package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.MacInterfacePO;
import org.springframework.stereotype.Repository;

@Repository
public interface MacInterfaceService {

    /*
    *
    * */
    MacInterfacePO selectOne(MacInterfacePO macInterfacePO);
}
