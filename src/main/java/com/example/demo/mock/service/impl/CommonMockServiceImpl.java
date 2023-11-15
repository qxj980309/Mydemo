package com.example.demo.mock.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.util.*;
import com.example.demo.mock.entity.po.*;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.RelatedApiMappeer;
import com.example.demo.mock.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class CommonMockServiceImpl implements CommonMockService {

    private static final Logger log = LoggerFactory.getLogger(CommonMockServiceImpl.class);

    private final RelatedApiMappeer relatedApiMapper;

    private final InterfaceCaseMapper interfaceCaseMapper;

    private final InterfaceCaseService interfaceCaseService;

    private final CommonMessageService commonMessageService;

    private final InterfaceService interfaceService;

    private final HttpStatus CONFIG_ERROR = HttpStatus.PRECONDITION_FAILED;

    public CommonMockServiceImpl(RelatedApiMappeer relatedApiMapper, InterfaceCaseMapper interfaceCaseMapper,
                                 InterfaceCaseService interfaceCaseService,CommonMessageService commonMessageService,
                                 InterfaceService interfaceService){
        this.relatedApiMapper = relatedApiMapper;
        this.interfaceCaseMapper = interfaceCaseMapper;
        this.interfaceCaseService = interfaceCaseService;
        this.commonMessageService = commonMessageService;
        this.interfaceService = interfaceService;
    }


    @Override
    public MockResponse mock(Long projectId, String txCode, String url, Map<String, String> headers, Map<String, Object> paramsMap) {
        InterfacePO interfacePO = selectInterface(projectId, txCode, url);
        String errMsg = ValidatorUtils.checkParam(paramsMap, interfacePO, getCommonMessage(interfacePO));
        log.info("请求参数校验错误信息:", errMsg);
        MockResponse mockResponse = getMockResponse(interfacePO, headers, paramsMap);
        mockResponse.setErrMsg(errMsg);
        return mockResponse;
    }

    @Override
    public MockResponse mock(Long projectId, String txCode, Map<String, Object> paramsMap) {
        return mock(projectId, txCode,null, null,paramsMap);
    }

    private MockResponse getMockResponse(InterfacePO interfacePO, Map<String, String> headers, Map<String, Object> paramsMap) {
        //获取多案例
        List<InterfaceCasePO> caseList = interfaceCaseService.selectByInterfaceId(interfacePO.getId());
        // 遍历所有案例，匹配期望
        for (InterfaceCasePO casePO : caseList) {
            if (isCaseMatching(headers, paramsMap, casePO)){
                Integer status = getHttpCode(casePO.getSimulateHttpCode());
                CommonUtils.postAction(casePO.getOperationType());
                saveRelatedApiData(casePO.getId(),paramsMap);

                if (casePO.getDynamicMessage() == null || casePO.getDynamicMessage().size() == 0) {
                    // 未配置动态报文，直接返回期望响应
                    return new MockResponse(status , casePO.getResponse());
                }else {
                    String dynamicRes = getDynamicRes(paramsMap, casePO, interfacePO.getDataType());
                    return new MockResponse(status, dynamicRes);
                }
            }
        }
        return new MockResponse(interfacePO.getDefaultResponse());
    }

    /*
    * 判断当前接口是否关联报文
    * */
    @Override
    public CommonMessagePO getCommonMessage(InterfacePO interFacePO) {
        IsRelatedPO isRelatedRequest = interFacePO.getIsRelatedRequest();
        if (ObjectUtil.isNotEmpty(isRelatedRequest)) {
            Long id = isRelatedRequest.getId();
            return commonMessageService.selectById(id);
        }
        return null;
    }

    /*
    * 存储该接口指定的数据
    * */
    private void saveRelatedApiData(Long caseId, Map<String, Object> body) {
        LambdaQueryWrapper<RelatedApiPO> query = Wrappers.lambdaQuery();
        query.select(RelatedApiPO::getId, RelatedApiPO::getName, RelatedApiPO::getValue)
                .eq(RelatedApiPO::getCaseId,caseId);
        List<RelatedApiPO> list = relatedApiMapper.selectList(query);
        for (RelatedApiPO relatedApiPO : list) {
            String value = JSONUtil.getValue(body, relatedApiPO.getName());
            log.info("存储接口关联数据: name=,value=",relatedApiPO.getName(),value);
            if (StringUtils.isNotBlank(value) && !value.equals(relatedApiPO.getValue())) {
                relatedApiPO.setValue(value);
                relatedApiMapper.updateById(relatedApiPO);
            }
        }
    }

    /*
    * 判断案例是否匹配
    * */
    private boolean isCaseMatching(Map<String, String> headers, Map<String, Object> bodyMap, InterfaceCasePO casePO) {
        for (ExpectationPO expectationPO : casePO.getExpectationList()) {
            String valve = CommonUtils.getValue(expectationPO.getLocation(), expectationPO.getKey(), headers, bodyMap);
            if(!ExpectationRuleUtil.isRuleMatching(
                    expectationPO.getCondition(), valve, getTargetValue(expectationPO.getValue()))) {
                return false;
            }
        }
        return true;
    }

    /*
     * 获取动态响应报文
     * */
    private String getDynamicRes(Map<String, Object> paramsMap, InterfaceCasePO casePO, String dataType) {
        // 动态报文
        Map<String, String> dynamicMap = DynamicMsgUtil.generateResponse(paramsMap, casePO.getDynamicMessage());
        String dynamicRes = DynamicMsgUtil.replaceResponse(casePO.getResponse(), dynamicMap, dataType);
        for (DynamicMsgPO dynamicMsgPO : casePO.getDynamicMessage()) {
            // 自增 if (dynamicMsgPo.getRule().equals(Constants.DYNAMIC_RULE_02)
            if (dynamicMsgPO.getRule().equals("02")) {
                dynamicMsgPO.setParam(dynamicMap.get(dynamicMsgPO.getKey()));
            }
        }
        interfaceCaseMapper.dynamicMsgUpdate(casePO.getId(),casePO.getDynamicMessage());
        return dynamicRes;
    }

    private String getTargetValue(String key){
        // #开头的数据取数据库中存储的值
        if (StringUtils.isNotBlank(key) && key.startsWith("#")) {
            LambdaQueryWrapper<RelatedApiPO> query = Wrappers.lambdaQuery();
            query.select(RelatedApiPO::getValue).eq(RelatedApiPO::getKey, key.substring(1));
            RelatedApiPO relatedApiPO = relatedApiMapper.selectOne(query);
            log.info("获取关联数据:key=,value=}",key,relatedApiPO.getValue());
            return relatedApiPO.getValue();
        }
        return key;
    }

    private Integer getHttpCode(SimulateHttpCodePO po){
        if (null == po || !po.getSwitchType()) {
            return null;
        }
        log.info("http状态码:{}",po.getStatusCode());
        int code;
        try {
            code = Integer.parseInt(po.getStatusCode());
        }catch (NumberFormatException e){
            log.error("http状态码配置错误，转换异常");
            return CONFIG_ERROR.value();
        }
        HttpStatus httpstatus = HttpStatus.resolve(code);
        return null == httpstatus ? CONFIG_ERROR.value() : httpstatus.value();
    }

    private InterfacePO selectInterface(Long projectId, String txCode, String url) {
        InterfacePO interfacePO = interfaceService.selectInterface(projectId, txCode, url);
//         Assert.notNull(interfacePO, ()-> new RuntimeException(MsgConstants.INTERFACE_NOT_FOUND));
        Assert.notNull(interfacePO, ()-> new RuntimeException("访问接口不存在"));
        return interfacePO;
    }
}
