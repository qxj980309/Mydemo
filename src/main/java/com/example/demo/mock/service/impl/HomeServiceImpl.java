package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.ProjectPo;
import com.example.demo.mock.entity.vo.ProVO;
import com.example.demo.mock.entity.vo.ProjectCountVo;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.InterfaceMapper;
import com.example.demo.mock.mapper.ProjectMapper;
import com.example.demo.mock.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HomeServiceImpl implements HomeService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private InterfaceMapper interfaceMapper;

    @Resource
    private InterfaceCaseMapper interfaceCaseMapper;

//    @Override
//    public ProjectCountVo allCount(){
//        //获取所有项目id
//        ProjectCountVo projectCountVo = new ProjectCountVo();
//        List<String> proNames = new ArrayList<>();
//        List<Integer> interfaceIds = new ArrayList<>();
//        List<Integer> caseIds = new ArrayList<>();
//        List<ProjectPo> projectPo = projectMapper.getAllProjectId();
//        if (CollectionUtils.isEmpty(projectPo)) return null;
//        int totalItf = 0, totalCase = 0;
//        for (ProjectPo  project: projectPo) {
//            proNames.add(project.getName());
//            List<Long> itfIds = interfaceMapper.alIdByProId(project.getId());
//            totalItf += itfIds.size();
//            interfaceIds.add(itfIds.size());
//            int count = 0;
//            for (Long itfId : itfIds) {
//                List<Long> cIds = interfaceCaseMapper.alIdByInterfaceId(itfId);
//                count += cIds.size();
//            }
//            totalCase += count;
//            caseIds.add(count);
//        }
//        proNames.add("项目总和");
//        interfaceIds.add(totalItf);
//        caseIds.add(totalCase);
//
//        projectCountVo.setName(proNames);
//        projectCountVo.setInterfaceCount(interfaceIds);
//        projectCountVo.setCaseCount(caseIds);
//
//
//        return projectCountVo;
//
//    }

    @Override
    public ProjectCountVo allCount(){
        //获取所有项目id
        ProjectCountVo projectCountVo = new ProjectCountVo();
        List<String> proNames = new ArrayList<>();
        List<Integer> interfaceIds = new ArrayList<>();
        List<Integer> caseIds = new ArrayList<>();
//        List<ProjectPo> projectPo = projectMapper.getAllProjectId();
//        if (CollectionUtils.isEmpty(projectPo)) return null;
//        int totalItf = 0, totalCase = 0;
//        for (ProjectPo  project: projectPo) {
//            proNames.add(project.getName());
//            List<Long> itfIds = interfaceMapper.alIdByProId(project.getId());
//            totalItf += itfIds.size();
//            interfaceIds.add(itfIds.size());
//            int count = 0;
//            for (Long itfId : itfIds) {
//                List<Long> cIds = interfaceCaseMapper.alIdByInterfaceId(itfId);
//                count += cIds.size();
//            }
//            totalCase += count;
//            caseIds.add(count);
//        }
        List<ProVO> interfaceMap= interfaceMapper.alIdByProId1();
        List<ProVO> caseMsp = interfaceCaseMapper.alIdByInterfaceId1();
        int totalItf = 0, totalCase = 0;
        for (ProVO itfVO : interfaceMap) {
            proNames.add(itfVO.getName());
            interfaceIds.add(itfVO.getCount());
            totalItf += itfVO.getCount();
            for (ProVO caseVo : caseMsp) {
                if (itfVO.getId().equals(caseVo.getId())){
                    caseIds.add(caseVo.getCount());
                    totalCase += caseVo.getCount();
                    break;
                }
            }

        }
        proNames.add("项目总和");
        interfaceIds.add(totalItf);
        caseIds.add(totalCase);

        projectCountVo.setName(proNames);
        projectCountVo.setInterfaceCount(interfaceIds);
        projectCountVo.setCaseCount(caseIds);


        return projectCountVo;

    }

    @Override
    public List<ProjectCountVo> allProjectCountVo() {
        List<ProjectCountVo> projectCountVoList = new ArrayList<>();
        List<ProjectPo> projectPo = projectMapper.getAllProjectId();
        if (CollectionUtils.isEmpty(projectPo)) return null;
        int totalItf = 0, totalCase = 0;
        for (ProjectPo  project: projectPo) {
            ProjectCountVo projectCountVo = new ProjectCountVo();
            projectCountVo.setName1(project.getName());
            List<Long> itfIds = interfaceMapper.alIdByProId(project.getId());
            totalItf += itfIds.size();
            projectCountVo.setInterfaceCount1(itfIds.size());
            int count = 0;
            for (Long itfId : itfIds) {
                List<Long> cIds = interfaceCaseMapper.alIdByInterfaceId(itfId);
                count += cIds.size();
            }
            totalCase += count;
            projectCountVo.setCaseCount1(count);
            projectCountVoList.add(projectCountVo);
        }
        ProjectCountVo projectCountVo = new ProjectCountVo();
        projectCountVo.setName1("总和");
        projectCountVo.setInterfaceCount1(totalItf);
        projectCountVo.setCaseCount1(totalCase);
        projectCountVoList.add(projectCountVo);

        return projectCountVoList;
    }

    @Override
    public List<ProjectCountVo> allProjectCountVo1() {
        List<ProjectCountVo> projectCountVoList = new ArrayList<>();
        List<ProVO> interfaceMap= interfaceMapper.alIdByProId1();
        List<ProVO> caseMsp = interfaceCaseMapper.alIdByInterfaceId1();
        int totalInter= 0 ,totalCase = 0;
        for (ProVO proVO : interfaceMap) {
            ProjectCountVo projectCountVo = new ProjectCountVo();
            projectCountVo.setName1(proVO.getName());
            projectCountVo.setInterfaceCount1(proVO.getCount());
            totalInter += proVO.getCount();
            for (ProVO vo : caseMsp) {
                if (proVO.getName().equals(vo.getName())){
                    projectCountVo.setCaseCount1(vo.getCount());
                    totalCase += vo.getCount();
                    break;
                }
            }
            projectCountVoList.add(projectCountVo);
        }

        ProjectCountVo projectCountVo = new ProjectCountVo();
        projectCountVo.setName1("总和");
        projectCountVo.setInterfaceCount1(totalInter);
        projectCountVo.setCaseCount1(totalCase);
        projectCountVoList.add(projectCountVo);

        return projectCountVoList;
    }


}
