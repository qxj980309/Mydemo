package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.InterfaceOperationPO;
import com.example.demo.mock.service.InterfaceOperationService;
import org.springframework.stereotype.Service;

@Service
public class InterfaceOperationServiceImpl implements InterfaceOperationService {
    @Override
    public InterfaceOperationPO selectByInterfaceId(Long interfaceId) {
        return null;
    }
}
