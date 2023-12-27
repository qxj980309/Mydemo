package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.MacInterfacePO;
import com.example.demo.mock.service.MacInterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MacInterfaceServiceImpl implements MacInterfaceService {
    @Override
    public MacInterfacePO selectOne(MacInterfacePO macInterfacePO) {
        return null;
    }
}
