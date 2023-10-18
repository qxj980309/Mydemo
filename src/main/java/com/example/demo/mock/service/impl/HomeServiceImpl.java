package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.ProjectPo;
import com.example.demo.mock.entity.vo.ProjectCountVo;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.InterfaceMapper;
import com.example.demo.mock.mapper.ProjectMapper;
import com.example.demo.mock.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private InterfaceMapper interfaceMapper;

    @Resource
    private InterfaceCaseMapper interfaceCaseMapper;

    @Override
    public ProjectCountVo allCount(){
        //获取所有项目id
        ProjectCountVo projectCountVo = new ProjectCountVo();
        List<String> proNames = new ArrayList<>();
        List<Integer> interfaceIds = new ArrayList<>();
        List<Integer> caseIds = new ArrayList<>();
        List<ProjectPo> projectPo = projectMapper.getAllProjectId();
        if (CollectionUtils.isEmpty(projectPo)) return null;
        int totalItf = 0, totalCase = 0;
        for (ProjectPo  project: projectPo) {
            proNames.add(project.getName());
            List<Long> itfIds = interfaceMapper.alIdByProId(project.getId());
            totalItf += itfIds.size();
            interfaceIds.add(itfIds.size());
            int count = 0;
            for (Long itfId : itfIds) {
                List<Long> cIds = interfaceCaseMapper.alIdByInterfaceId(itfId);
                count += cIds.size();
            }
            totalCase += count;
            caseIds.add(count);
        }
        proNames.add("项目总和");
        interfaceIds.add(totalItf);
        caseIds.add(totalCase);

        projectCountVo.setName(proNames);
        projectCountVo.setInterfaceCount(interfaceIds);
        projectCountVo.setCaseCount(caseIds);


        return projectCountVo;

    }



}
