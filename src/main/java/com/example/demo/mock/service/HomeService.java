package com.example.demo.mock.service;

import com.example.demo.mock.entity.vo.HomeContVO;
import com.example.demo.mock.entity.vo.ProjectCountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeService {

    HomeContVO selectCount();

    ProjectCountVo allCount();

    List<ProjectCountVo> allProjectCountVo();

    List<ProjectCountVo> allProjectCountVo1();
}
