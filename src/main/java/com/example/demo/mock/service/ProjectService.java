package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.ProjectPO;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectService {
    /*
    * 根据系统号查询
    * */
    ProjectPO selectBySysCode(String sysCode);
}
