package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.MacPO;
import com.example.demo.mock.entity.po.ProjectPO;
import com.example.demo.mock.service.MacService;
import com.example.demo.mock.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Override
    public ProjectPO selectBySysCode(String sysCode) {
        return null;
    }
}
