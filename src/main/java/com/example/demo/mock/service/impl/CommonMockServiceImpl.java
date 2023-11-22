package com.example.demo.mock.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.mock.common.Constants.Constants;
import com.example.demo.mock.common.Constants.MsgConstants;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.enums.DataTypeEnum;
import com.example.demo.mock.common.util.*;
import com.example.demo.mock.entity.po.*;
import com.example.demo.mock.mapper.InterfaceCaseMapper;
import com.example.demo.mock.mapper.RelatedApiMappeer;
import com.example.demo.mock.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonMockServiceImpl implements CommonMockService {
    private static final Logger log = LoggerFactory.getLogger(CommonMockServiceImpl.class);

    private final RelatedDataService relatedDataService;

    private final RelatedApiMappeer relatedApiMapper;

    private final InterfaceCaseMapper interfaceCaseMapper;

    private final InterfaceCaseService interfaceCaseService;

    private final CommonMessageService commonMessageService;

    private final InterfaceService interfaceService;

    private final MockLogService mockLogService;

    private final MacInterfaceService macInterfaceService;

    private final MacService macService;

    private final InterfaceOperationService interfaceOperationService;

    private final HttpStatus CONFIG_ERROR = HttpStatus.PRECONDITION_FAILED;

    public CommonMockServiceImpl(RelatedDataService relatedDataService,RelatedApiMappeer relatedApiMapper, InterfaceCaseMapper interfaceCaseMapper,
                                 InterfaceCaseService interfaceCaseService,CommonMessageService commonMessageService,
                                 InterfaceService interfaceService,MacInterfaceService macInterfaceService,
                                 MacService macService,InterfaceOperationService interfaceOperationService,
                                 MockLogService mockLogService){
        this.relatedApiMapper = relatedApiMapper;
        this.interfaceCaseMapper = interfaceCaseMapper;
        this.interfaceCaseService = interfaceCaseService;
        this.commonMessageService = commonMessageService;
        this.interfaceService = interfaceService;
        this.macInterfaceService = macInterfaceService;
        this.macService = macService;
        this.interfaceOperationService =interfaceOperationService;
        this.mockLogService = mockLogService;
        this.relatedDataService =relatedDataService;
    }

//    @Override
//    public MockResponse mock(Long projectId, String txCode, String url, Map<String, String> headers, Map<String, Object> paramsMap) {
//        InterfacePO interfacePO = selectInterface(projectId, txCode, url);
//        String errMsg = ValidatorUtils.checkParam(paramsMap, interfacePO, getCommonMessage(interfacePO));
//        log.info("请求参数校验错误信息:", errMsg);
//        MockResponse mockResponse = getMockResponse(interfacePO, headers, paramsMap);
//        mockResponse.setErrMsg(errMsg);
//        return mockResponse;
//    }
    @Override
    public MockResponse mock(InterfacePO interfacePO, Map<String, String> headers, Map<String, Object> paramsMap) {
        //异步-存数据至log表
        String traceId = MDC.get("X-B3-TraceId");
        mockLogService.saveMockLog(interfacePO,traceId);

        //请求参数校验错误信息
        String errMsg = ValidatorUtils.checkParam(paramsMap, interfacePO, getCommonMessage(interfacePO));
        if(errMsg.length()>0){
            log.info("请求参数校验错误信息:{}", errMsg);
            //返回报文
            Map<String ,Object> paramFailResMap = new HashMap<>();
            paramFailResMap.put("paramErrMag", MsgConstants.PARAM_CHECK_FAIL);
            paramFailResMap.put("code",MsgConstants.PARAM_CHECK_FAIL_CODE);
            paramFailResMap.put("message",errMsg);
            return errRes(paramFailResMap,interfacePO.getDataType());
        }

        //校验MAC
        Boolean checkMac = checkMac(interfacePO,paramsMap);
        if (checkMac == false){
            log.info("MAC校验错误信息:{}", errMsg);
            //返回报文
            Map<String ,Object> macFailResMap = new HashMap<>();
            macFailResMap.put("paramErrMag", MsgConstants.MAC_CHECK_FAIL);
            macFailResMap.put("code",MsgConstants.MAC_CHECK_FAIL_CODE);
            macFailResMap.put("message",errMsg);
            return errRes(macFailResMap,interfacePO.getDataType());
        } else if (checkMac){
            log.info("MAC校验成功");
        }

        //请求报文字段解析
        InterfaceOperationPO interfaceOperationPO = interfaceOperationService.selectByInterfaceId(interfacePO.getId());
        if (interfaceOperationPO != null && interfaceOperationPO.getFieldParseList() != null) {
            log.info("请求报文字段解析");
            for (FieldParsePO po : interfaceOperationPO.getFieldParseList()) {
                InterfaceOperationUtil.fieldParse(paramsMap, po.getName(), po.getStructure());
            }
        }

        //匹配多案例
        MockResponse mockResponse = getMockResponse(interfacePO, headers, paramsMap);
        mockResponse.setErrMsg(errMsg);

        // 生成MAC
        mockResponse.setBody(getMac(interfacePO, mockResponse.getBody()));

        // 响应报文是否生成签名

        if (interfaceOperationPO != null && interfaceOperationPO.getSignature() != null){
            mockResponse.setBody(getSignatureRes(interfaceOperationPO.getSignature(),mockResponse.getBody(),interfacePO.getDataType()))
        }
            return mockResponse;
    }

    /*
    * 请求参数/MAC校验失败  根据数据格式直接返回校验失败报文
    *
    *  @param failResMap
    *  @param dataType 数据式 json/xml
    * @return
    * */
    private MockResponse errRes(Map<String, Object> macFailResMap, String dataType) {
        switch (dataType) {
            case Constants.DATA_TYPE_JSON:
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    return new MockResponse(objectMapper.writeValueAsString(macFailResMap));
                } catch (JsonProcessingException e) {
                    log.info("“map对象转换为json格式字符串异常",e);
                }
            case Constants.DATA_TYPE_XML:
                try{
                    XmlMapper xmlMapper  = new XmlMapper();
                    String xmlString = xmlMapper.writeValueAsString(macFailResMap);
                    xmlString = xmlString.replace(  "<HashMap>","");
                    xmlString = xmlString.replace( "</HashMap>","");
                    String preXml = "<?xml version= \"1.0\" encoding= \"UTF-8 \" ?>";
                    return new MockResponse(preXml.concat("<root>").concat(xmlString).concat("</root>"));
                } catch (JsonProcessingException e) {
                    log.info("map对象转换为xml格式字符串异常",e);
                }
            default:
                log.info("当前格式暂不支持");
        }
        return new MockResponse(null);
    }

    /*
    * 请求报文验mac
    *
    *  @param interfacePo
    * @param paramsMap
    * */
    private Boolean checkMac(InterfacePO interfacePO, Map<String, Object> paramsMap) {
        MacInterfacePO requestPO = new MacInterfacePO();
        requestPO.setInterfaceId(interfacePO.getId());
        requestPO.setApplicationScope(Constants.MAC_APPLICATION_SCOPE_REQUEST);
        requestPO = macInterfaceService.selectOne(requestPO);
        if(requestPO != null && requestPO.getMacId() != null){
            MacPO macPO = macService.selectById(requestPO.getMacId());
            String macData = MACUtil.compositionData(paramsMap, macPO.getCompositionRule(), macPO.getDataElement());
            String requestMac = MACUtil.getValueByMacKey(paramsMap, macPO.getMacKey());
            log.info("请求报文的mac串={}，mac值={}", macData,requestMac);
            return MACUtil.checkMac(macPO.getbenerateRule().getAlgorithmtype(),macPO.getenerateRule().getSendsecretkey(),macData,requestMac);
        }
        return null;
    }

    /*
    *响应报文生成MaC
    *
    * @param interfacePO
    * @param response
    * @return
    * */
    private String getMac(InterfacePO interfacePO, String response) {
        Map<String, Object> resMap = CommonUtils.parseBody(DataTypeEnum.codeTypeName(interfacePO.getDataType()),response);
        MacInterfacePO responsePO = new MacInterfacePO();
        responsePO.setInterfaceId(interfacePO.getId());
        responsePO.setApplicationScope(Constants.MAC_APPLICATION_SCOPE_RESPONSE);
        responsePO = macInterfaceService.selectOne(responsePO);
        if(responsePO != null && responsePO.getMacId() != null){
            MacPO macPO = macService.selectById(responsePO.getMacId());
            String macData = MACUtil.compositionData(resMap, macPO.getCompositionRule(), macPO.getDataElement());
            String mac = MACUtil.getMac(macPO.getbenerateRule().getAlgorithmType(),macPO.getbenerateRule().getAccSecretkey(), macData);
            String macFullKey = MACUtil.getMacFullKey(resMap, macPO.getMacKey());
            log.info("响应报文的mac串={}，mac值={}，mac参数名={}",macData,mac,macFullKey);
            Map<String,String> MacMap = new HashMap<>();
            MacMap.put(macFullKey, mac);
            return ReplaceResponseUtil.replaceResponse(response, MacMap, interfacePO.getDataType());
        }
        return response;
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
             //自增
            if (dynamicMsgPO.getRule().equals(Constants.DYNAMIC_RULE_02) ||
                    dynamicMsgPO.getRule().equals(Constants.DYNAMIC_RULE_05)){
                // 去掉拼接字符,再写回数据库
                String str = dynamicMsgPO.getStr();
                String param = dynamicMap.get(dynamicMsgPO.getKey());
                if (str != null && param.contains(str)) {
                    param = param.substring(str.length());
                }
                dynamicMsgPO.setParam(param) ;
//                dynamicMsgPO.setParam(dynamicMap.get(dynamicMsgPO.getKey()));
            }
        }
        interfaceCaseMapper.dynamicMsgUpdate(casePO.getId(),casePO.getDynamicMessage());
        return dynamicRes;
    }

    /*
    *  以#开头的数据取数据中存储的值 #o,b,#c 表示和C两个值会通过查询数据库得到，b原样返回
    *
    *  @param key key
    *  @return 转换后的key
    * */
    private String getTargetValue(String key){
        // #开头的数据取数据库中存储的值
        if (StringUtils.isBlank(key) || !key.contains(Constants.DATABASE_PREFIX)) {
            return key;
        }
        String[] keys = ExpectationRuleUtil.splitKeys(key);
        // 需要查询数据库转换的值
        Map<String, Integer> keyMap = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        // 结果
        String[] retArray = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].startsWith(Constants.DATABASE_PREFIX)) {
                // 以#号开头，查询数据库
                String str = keys[i].substring(1);
                keyMap.put(str, i);
                keyList.add(str);
            } else {
                // 不以#号开头，不需要查询数据库
                retArray[i] = keys[i];
            }
        }
        // 将查询结果塞到ret中
        List<RelatedApiPO> list = relatedDataService.selectRelatedData(keyList);
        for (RelatedApiPO relatedApiPO : list) {
            log.info("获取关联数据:key={},value={}",relatedApiPO.getKey(),relatedApiPO.getValue());
            Integer index = keyMap.get(relatedApiPO.getKey());
            retArray[index] = relatedApiPO.getValue();
        }
        return transformString(retArray);;
    }

    private String transformString(String[] retArray) {
        StringBuilder ret = new StringBuilder();
        for (String s : retArray) {
            ret.append(s).append(",");
        }
        return ret.deleteCharAt( ret.length() - 1).toString();
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

    /*
    * 返回 签名 + 响应报文
    *
    *  @param signaturePO 响应报文生成签名实体类
    *  @param response 响应报文
    *  @param dataType 数据格式
    *  @return
    * */
    private String getSignatureRes(SignaturePO signaturePO, String response, String dataType) {
        String signatureValue = getsignaturevalue(signaturePO.getAlgorithm(), signaturePO.getsecretkey(), response);
        switch (signaturePO.getLocation()) {
            case Constants.SIGNATURE_LOCATION_BEGIN:

        }

    }

    /*
    *生成签名值
    *
    *  @param algorithm 签名算法
    * @param secretKey 秘钥
    * @param response响应报文
    * @return 签名值
    * */
    private String getSignatureValue(String algorithm, String secretKey, String response) {

    }

    private InterfacePO selectInterface(Long projectId, String txCode, String url) {
        InterfacePO interfacePO = interfaceService.selectInterface(projectId, txCode, url);
//         Assert.notNull(interfacePO, ()-> new RuntimeException(MsgConstants.INTERFACE_NOT_FOUND));
        Assert.notNull(interfacePO, ()-> new RuntimeException("访问接口不存在"));
        return interfacePO;
    }
}
