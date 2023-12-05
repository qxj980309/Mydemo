package com.example.demo.mock.common.util;

import com.example.demo.mock.common.Constants.Constants;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.enums.DataTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseUtil {
    private final static Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    /*
    * 请求参数/MAC 校验失败 根据数据格式直接返回校验失败报文
    *  @param
    *  @param
    *  @param dataType 数据格式 json/xml
    *  @return
    * */
    public static String errStrDataType(String code, String message, String dataType) {
        Map<String, Object> failResMap = new HashMap<>();
        failResMap.put("code", code);
        failResMap.put("message", message);

        switch (dataType) {
            case Constants.DATA_TYPE_JSON:
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(failResMap);
                } catch (JsonProcessingException e) {
                    log.error("map对象转换为json格式字符串异常",e);
                }
            case Constants.DATA_TYPE_XML:
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    String xmlString = xmlMapper.writeValueAsString(failResMap);
                    xmlString = xmlString.replace("<HashMap>","");
                    xmlString = xmlString.replace("</HashMap>","");
                    String preXml = "<?xml version= \"1.0\" encoding= \"UTF-8\" ?>";
                    return preXml.concat("<root>").concat(xmlString).concat("</root>");
                } catch (JsonProcessingException e) {
                    log.error("map对象转换为xml格式字符串异常",e);
                }
            default:
                log.info("当前格式暂不支挣");
        }
        return null;
    }

    /*
    * contentType转dataType
    * */
    public static String convertDatatype(String contentType) {
        if (null ==contentType || contentType.contains(DataTypeEnum.json.getName()) || contentType.contains("text/plain")){
            return "02";
        } else if (contentType.contains(DataTypeEnum.XML.getName()) || contentType.contains("text/xml")){
            return "01";
        }
        return "";
    }

    /*
    * 请求参数/MAC校验失败 限报数格式直提运园校验失败报文
     *  @param
     *  @param
     *  @param dataType 数据格式 json/xml
     *  @return
    * */
    public static MockResponse errResDataType(String code, String message, String dataType ){
        return new MockResponse(errStrDataType(code,message,dataType));
    }

    /*
     * 请求参数/MAC校验失败 限报数格式直提运园校验失败报文
     *  @param
     *  @param
     *  @param contentType
     *  @return
     * */
    public static MockResponse errResContentType(String code, String message, String contentType ){
        return new MockResponse(errStrDataType(code,message,convertDatatype(contentType)));
    }

}
