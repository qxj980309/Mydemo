package com.example.demo.mock.common.util;

import cn.hutool.core.util.XmlUtil;
import com.example.demo.mock.common.constants.Constants;
import com.example.demo.mock.common.enums.DataTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

public class ReplaceResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(ReplaceResponseUtil.class);

    /*
    * 取出key对应最后一个层级MAP
    *
    * @param keyList key 可能包含多层级
    * @param currentMap 相应报文
    * @return key 对应最后一个层级MAP
    * */
    public static Map<String ,Object> getLastLayer(String[] keyList,Map<String ,Object> currentMap){
        //去最后一个层级的Map，所以遍历到keyList。length-1
        for (int i = 0 ; i < keyList.length-1; i++){
            String key = keyList[i];
            if (!currentMap.containsKey(key)){
                log.warn("报文没有该字段{}",key);
                return null;
            }
            if (!(currentMap.get(key) instanceof Map)){
                log.warn("报文替换字段层级与报文层级不匹配");
                return null;
            }
            currentMap = (Map<String ,Object>) currentMap.get(key);
        }
        return currentMap;
    }

    /*
    * 替换默认响应中的默认值
    *
    * @param response 响应报文
    * @param signatureMap 需要替换的值
    * @param dataType 数据格式
    * @return 替换后报文
    * */
    public static String replaceResponse(String response, Map<String, String> signatureMap, String dataType) {
        String result = null;
        switch (dataType){
            case Constants.DATA_TYPE_JSON:
                result = setJsonKeyValve(response,signatureMap,dataType);
                break;
            case Constants.DATA_TYPE_XML:
                result = setXmlKeyValue(response,signatureMap,dataType);
            default:
                log.info("当前格式不支持");
        }
        return result;
    }

    /*
    * 替换XML字段
    *
     * @param response 响应报文
     * @param signatureMap 需要替换的值
     * @param dataType 数据格式
     * @return 替换后报文
    * */
    private static String setXmlKeyValue(String response, Map<String, String> signatureMap, String dataType) {
        Document doc = XmlUtil.parseXml(response);
        Element root = doc.getDocumentElement();
        for (String key : signatureMap.keySet()) {
            String[] keyList = JSONUtil.getKeyList(key);
            String value = signatureMap.get(key);
            Element temp = (Element) root.getElementsByTagName(keyList[keyList.length - 1]).item(0);
            if (temp == null) {
                log.warn("响应报文没有当前字段{}",key);
                break;
            }
            temp.setTextContent(value);
        }
        return XmlUtil.toStr(doc);
    }

    /*
     * 替换JSON字段
     *
     * @param response 响应报文
     * @param signatureMap 需要替换的值
     * @param dataType 数据格式
     * @return 替换后报文
     * */
    private static String setJsonKeyValve(String response, Map<String, String> signatureMap, String dataType) {
        Map<String, Object> bodyMap = CommonUtils.parseBody(DataTypeEnum.codeTypeName(dataType), response);
        //JSON 报文
        for (String key : signatureMap.keySet()) {
            String[] keyList = JSONUtil.getKeyList(key);
            String value = signatureMap.get(key);
            setJsonKeyValue(keyList, bodyMap, value);
        }
        return bodyMap.toString();
    }

    /*
    * 替换JSON报文型map中的value值
    *
    *  @param keyList key,可能包合多层级
    *  @param bodyMap 响应报文
    *  @param value替换值
    * */
    private static void setJsonKeyValue(String[] keyList, Map<String, Object> bodyMap, String value) {
        Map<String, Object> currentMap = bodyMap;
        currentMap = getLastLayer(keyList, currentMap);
        if (currentMap == null) {
            return;
        }
        String lastKey = keyList[keyList.length - 1];
        currentMap.put(lastKey, value);
    }
}
