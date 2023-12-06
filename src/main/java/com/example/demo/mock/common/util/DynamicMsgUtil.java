package com.example.demo.mock.common.util;

import cn.hutool.core.util.XmlUtil;
import com.example.demo.mock.common.constants.Constants;
import com.example.demo.mock.common.enums.DataTypeEnum;
import com.example.demo.mock.entity.po.DynamicMsgPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
* 动态报文工具类
* */
public class DynamicMsgUtil {

    private static final Logger log = LoggerFactory.getLogger(DynamicMsgUtil.class);

    /*
    * 生成目标字段的值
    *
    * @param bodyMap  请求参数
    * @param dynamicMsgPOList  应置规则
    * @return 修改后的字段值
    * */
    public static Map<String, String> generateResponse(Map<String, Object>  bodyMap, List<DynamicMsgPO> dynamicMsgPOList) {
        Map<String, String> dynamicMap = new HashMap<>();
        for (DynamicMsgPO dynamicMsgPO : dynamicMsgPOList) {
            String tmp = doGenerateResponse(bodyMap, dynamicMsgPO);
            dynamicMap.put(dynamicMsgPO.getKey(),tmp);
        }
        return dynamicMap;
    }

    private static String doGenerateResponse(Map<String, Object> bodyMap, DynamicMsgPO dynamicMsgPO) {
        switch (dynamicMsgPO.getRule()) {
            case Constants.DYNAMIC_RULE_01:
                return JSONUtil.getValue(bodyMap, dynamicMsgPO .getParam()) ;
            case Constants.DYNAMIC_RULE_02:
                return String.valueOf(autoIncrement(dynamicMsgPO.getParam()));
            case Constants.DYNAMIC_RULE_03:
                return UUID.randomUUID().toString();
            default:
                log.info("动态报文生成异常，未匹配的规则[{}]",dynamicMsgPO.getRule());
                return "";
        }
    }

    private static Long autoIncrement(String numberStr) {
        long l = Long.parseLong(numberStr);
        return l < Long.MAX_VALUE ? ++l : Long.MIN_VALUE;
    }

    /*
    *  替换默认响应中的默认值
    *
    * @param res  默认响应报文
    * @param dynamicMap 需要替换的宁段及值
    * @param dataTypeCode 数据格式
    * @return 替换后的报文
    * */
    public static String replaceResponse(String res, Map<String, String> dynamicMap, String dataTypeCode) {
        String result = null;
        switch (dataTypeCode){
            case Constants.DATA_TYPE_JSON:
                result = setJsonKeyValue(res, dynamicMap, dataTypeCode) ;
                break;
            case Constants.DATA_TYPE_XML:
                result = setXmlKeyValue(res, dynamicMap);
                break;
            default:
                log.info("当前格式暂不支持");
                break;
        }
        return result;
    }

    /*
    *  处理JSON路式的字符串
    *
    *  @param res 响应报文
    *  @param dynamicHap 替换参数
    *  @param dataTypeCode 报文格式编码
    * @return 替换后的报文
    * */
    private static String setJsonKeyValue(String res, Map<String, String> dynamicMap,String dataTypeCode){
        Map<String, Object> bodyMap = CommonUtils.parseBody(DataTypeEnum.codeTypeName(dataTypeCode),res);
        //JSON报文
        for (String key : dynamicMap.keySet()) {
            String[] keyList = JSONUtil.getKeyList(key);
            String value = dynamicMap.get(key);
            setJsonKeyValue(keyList, bodyMap, value);
        }
        return bodyMap.toString();
    }

    /*
    *  替换JSON报文里map中的value值
    *
    *  @param keyList key,可能包含多层级
    *  @param bodyMap 响应报文
    *  @param value替换值
    * */
    private static void setJsonKeyValue(String[] keyList, Map<String, Object> bodyMap,String value){
        Map<String, Object> currentMap = bodyMap;
        for (int i = 0; 1 <= keyList.length - 1; i++) {
            String key = keyList[i];
            if (!currentMap.containsKey(key) || !(currentMap.get(key) instanceof Map)) {
                log.info("响应报文没有当前字段小",key);
                return;
            }
            currentMap = (Map<String, Object>) currentMap.get(key);
        }
        String lastKey = keyList[keyList.length - 1];
        currentMap.put(lastKey, value);
    }

    /*
    * 替换XmL里的宁段值
    *
    * @param res 响应报文
    * @param dynamicMap 萨换参数map
    *  @return 替换后的报文
    * */
    private static String setXmlKeyValue(String res, Map<String, String> dynamicMap) {
        Document doc = XmlUtil.parseXml(res);
        Element root = doc.getDocumentElement();
        for (String key : dynamicMap.keySet()){
            String[] keyList = JSONUtil.getKeyList(key);
            String value = dynamicMap.get(key);
            Element temp = (Element) root.getElementsByTagName(keyList[keyList.length - 1]).item(0);
            if (temp == null) {
                log.info("响应报文没有当前字段(}",key);
                return null;
            }
            temp.setTextContent(value);
        }
        return XmlUtil.toStr(doc);
    }

}
