package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.InterfacePO;
import com.example.demo.mock.service.InterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService {
    @Override
    public InterfacePO selectInterface(Long projectId, String txCode, String url) {
        return null;
    }

    @Override
    public List<InterfacePO> selectInterface1(Long projectId, String txCode, String url) {
        return null;
    }

    @Override
    public List<InterfacePO> selectList(InterfacePO interfacePO) {
        return null;
    }
}
