package com.example.demo.mock.service.impl;

import cn.hutool.core.lang.Assert;
import com.example.demo.mock.common.Constants.Constants;
import com.example.demo.mock.common.Constants.MsgConstants;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.entity.UrlEntity;
import com.example.demo.mock.common.enums.DataTypeEnum;
import com.example.demo.mock.common.enums.PlatformCodeEnum;
import com.example.demo.mock.common.exception.CustomResponseException;
import com.example.demo.mock.common.util.CommonUtils;
import com.example.demo.mock.common.util.SaveDataTypeThread;
import com.example.demo.mock.entity.po.InterfaceOperationPO;
import com.example.demo.mock.entity.po.InterfacePO;
import com.example.demo.mock.entity.po.ProjectPO;
import com.example.demo.mock.entity.po.RoutePO;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.RelatedApiMappeer;
import com.example.demo.mock.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpMockServiceImpl extends CommonMockServiceImpl implements HttpMockService {
    private static final Logger log = LoggerFactory.getLogger(HttpMockServiceImpl.class);
//    @Resource
//    private  ProjectService projectService;
//    @Resource
//    private  RouteService routeService;

    private final ProjectService projectService;

    private final RouteService routeService;

    private final InterfaceService interfaceService;

    private final InterfaceOperationService interfaceOperationService;

    public HttpMockServiceImpl(RelatedApiMappeer relatedApiMapper, InterfaceCaseMapper interfaceCaseMapper,
                               InterfaceCaseService interfaceCaseService, CommonMessageService commonMessageService,
                               InterfaceService interfaceService, MacInterfaceService macInterfaceService,
                               MacService macService,ProjectService projectService,RouteService routeService,
                               InterfaceOperationService interfaceOperationService,MockLogService mockLogService,
                               RelatedDataService relatedDataService) {
        super(relatedDataService,relatedApiMapper, interfaceCaseMapper, interfaceCaseService, commonMessageService, interfaceService,
                macInterfaceService, macService,interfaceOperationService,mockLogService);
        this.projectService = projectService;
        this.routeService = routeService;
        this.interfaceService = interfaceService;
        this.interfaceOperationService = interfaceOperationService;
    }

    @Override
    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, String body) {
        String contentType;
        InterfacePO interfacePO = interfaceParse(urlEntity.getUrl());
        if (interfacePO!=null){
            //http post 存 datatype
            contentType = DataTypeEnum.codeTypeName(interfacePO.getDataType());
            SaveDataTypeThread.getInstance().setDataType(contentType);
            //请求报文预处理
            body = pretreatment(interfacePO.getId(),body);
        } else {
            contentType = headers.get("content-type");
            SaveDataTypeThread.getInstance().setDataType(contentType);
        }
        // 解析body中的数据
//        String contentType = headers.get("content-type");
        Map<String,Object> bodyMap = CommonUtils.parseBody(contentType, body);
//        return  mock(urlEntity, headers, bodyMap);
        return interfacePO == null ? mock(urlEntity, headers, bodyMap) :mock(interfacePO, headers, bodyMap);
    }

    @Override
    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, String params, String characterEncoding) {
        String contentType;
        //直接通过url定位接口
        InterfacePO interfacePO = interfaceParse(urlEntity.getUrl());
        if (interfacePO!=null){
            //http get 存 datatype
            contentType = DataTypeEnum.codeTypeName(interfacePO.getDataType());
            SaveDataTypeThread.getInstance().setDataType(contentType);
            //请求报文预处理
            params = pretreatment(interfacePO.getId(),params);
        }else {
            contentType = headers.get("content-type");
            SaveDataTypeThread.getInstance().setDataType(contentType);
        }
        Map<String, Object> paramsMap = new HashMap<>();
        if(StringUtils.isNotEmpty(params)){
            // 解析ur飞中的数据
            paramsMap = CommonUtils.getParams(params, characterEncoding);
        }
//        return mock(urlEntity, headers, paramsMap);
        return interfacePO == null ? mock(urlEntity, headers, paramsMap) :mock(interfacePO, headers, paramsMap);
    }

    public MockResponse mock(UrlEntity urlEntity, Map<String, String> headers, Map<String, Object> paramsMap){
        // 获取工程
        ProjectPO projectPO = projectService.selectBySysCode(urlEntity.getReceiveSysCode());
//        Assert.notNull(projectPO, () -> new NotFoundException(HsgConstants.SYSTEM_NO_FOUND));
        //获取路由
        RoutePO routePO =routeService.selectByUrl(projectPO.getId(), urlEntity.getUrl());
        //获取接口
        String txCode = CommonUtils.getTxCode(routePO, headers, paramsMap);
        if(StringUtils.isBlank(txCode) && StringUtils.isBlank(urlEntity.getUrl())){
            log.error("http:接口编号和url不能为空");
            throw new CustomResponseException(PlatformCodeEnum.FZ_M0006.getCode(),PlatformCodeEnum.FZ_M0006.getMessage(),SaveDataTypeThread.getInstance().getDataType());
        }
        //通过路由定位接口
        InterfacePO interfacePO = selectInterface(projectPO.getId(),txCode,urlEntity.getUrl());
        return mock(interfacePO, headers, paramsMap);
    }

    /*
    * 通过路由定位接口
    *
    *
    * */
//    private InterfacePO selectInterface(Long projectId,String txCode,String url){
//        InterfacePO interfacePO = interfaceService.selectInterface(projectId,txCode,url);
//        Assert.notNull(interfacePO,()->new RuntimeException(MsgConstants.INTERFACE_NOT_FOUND));
//        return interfacePO;
//    }

    /*
    * 接口解析 通过接口地址定位接口
    *
    * @url
    * @return InterfacePO对象
    * */
    private InterfacePO interfaceParse(String url) {
        InterfacePO interfacePO = new InterfacePO();
        interfacePO.setUrl(url);
        List<InterfacePO> interfacePOList = interfaceService.selectList(interfacePO);
        return interfacePOList.size() == 1 ? interfacePOList.get(0) : null;
    }

    /*
    * 请求报文预处理
    *
    * @params interfaceId 接口id
    * @params body 请求报文
    * @return 处理完的报文
    * */
    private String pretreatment(Long interfaceId, String body) {
        InterfaceOperationPO interfaceOperationPO = interfaceOperationService.selectByInterfaceId(interfaceId);
        if (interfaceOperationPO == null || interfaceOperationPO.getPretreatment()==null){
            return body;
        } else if(Constants.PRETREATMENT_CUT.equals(interfaceOperationPO.getPretreatment().getType())){
            return body.substring(body.indexOf(interfaceOperationPO.getPretreatment().getDelimiter()));
        } else {
            log.info("当前预处理类型不支持");
            throw new RuntimeException("当前预处理类型不支持");
        }
    }
}
