package com.example.demo.mock.service.impl;

import cn.hutool.core.lang.Assert;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.entity.UrlEntity;
import com.example.demo.mock.common.util.CommonUtils;
import com.example.demo.mock.entity.po.ProjectPO;
import com.example.demo.mock.entity.po.RoutePO;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.RelatedApiMappeer;
import com.example.demo.mock.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class HttpMockServiceImpl extends CommonMockServiceImpl implements HttpMockService {
    private static final Logger log = LoggerFactory.getLogger(HttpMockServiceImpl.class);
//    @Resource
//    private  ProjectService projectService;
//    @Resource
//    private  RouteService routeService;

    private final ProjectService projectService;

    private final RouteService routeService;

    public HttpMockServiceImpl(RelatedApiMappeer relatedApiMapper, InterfaceCaseMapper interfaceCaseMapper,
                               InterfaceCaseService interfaceCaseService, CommonMessageService commonMessageService,
                               InterfaceService interfaceService,ProjectService projectService,RouteService routeService) {
        super(relatedApiMapper, interfaceCaseMapper, interfaceCaseService, commonMessageService, interfaceService);
        this.projectService = projectService;
        this.routeService = routeService;
    }

    @Override
    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, String body) {
        // 解析body中的数据
        String contentType = headers.get("content-type");
        Map<String,Object> bodyMap = CommonUtils.parseBody(contentType, body);
        return  mock(urlEntity, headers, bodyMap);
    }

    @Override
    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, String params, String characterEncoding) {
        Map<String, Object> paramsMap = new HashMap<>();
        if(StringUtils.isNotEmpty(params)){
            // 解析ur飞中的数据
            paramsMap = CommonUtils.getParams(params, characterEncoding);
        }
        return mock(urlEntity, headers, paramsMap);
    }

    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, Map<String, Object> paramsMap){
        // 获取工程
        ProjectPO projectPO = projectService.selectBySysCode(urlEntity.getReceiveSysCode());
//        Assert.notNull(projectPO, () -> new NotFoundException(HsgConstants.SYSTEM_NO_FOUND));
        Assert.notNull(projectPO, () -> new RuntimeException("接收方系统好不存在"));
        //获取路由
        RoutePO routePO =routeService.selectByUrl(projectPO.getId(), urlEntity.getUrl());
        //获取接口
        String txCode = CommonUtils.getTxCode(routePO, headers, paramsMap);
        return mock(projectPO.getId(), txCode, urlEntity.getUrl(), headers, paramsMap);
    }
}
