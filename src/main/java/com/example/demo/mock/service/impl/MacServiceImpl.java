package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.MacPO;
import com.example.demo.mock.service.MacService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MacServiceImpl implements MacService {
    @Override
    public MacPO selectById(Long macId) {
        return null;
    }
}
