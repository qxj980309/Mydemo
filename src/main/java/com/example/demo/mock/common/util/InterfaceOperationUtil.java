package com.example.demo.mock.common.util;


import com.example.demo.mock.common.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterfaceOperationUtil {

    private static final Logger log = LoggerFactory.getLogger(InterfaceOperationUtil.class);

    /*
    * 请求字段解析
    * @param bodyMap 请求报文
    * @param parseName 参数名
    * @param structure 解析结构
    * */
    public static void fieldParse(Map<String, Object> bodyMap, String parseName, String structure) {
        String[] parseNameList = JSONUtil.getKeyList(parseName);
        Map<String, Object> currentMap = ReplaceResponseUtil.getLastLayer(parseNameList,bodyMap);
        String lastParseName = parseNameList[parseNameList.length - 1];
        String value = currentMap.get(lastParseName).toString();
        switch (structure){
            case Constants.STRUCTURE_JSONOBJECT:
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object object = objectMapper.readValue(value, Object.class);
                    currentMap.put(lastParseName, object);
                }catch (Exception e){
                    log.error("请求报文字段JSONObject结构解析异常",e);
                }
                break;
            case Constants.STRUCTURE_JSONARRAY:
                try {
                    JSONArray jsonArray = new JSONArray(value);
                    List<Object> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length();i++) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Object object = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), Object.class);
                        list.add(object);
                    }
                    currentMap.put(lastParseName, list);
                }catch (Exception e){
                    log.error("请求报文字段JSONObject结构解析异常",e);
                }
                break;
            default:
                log.info("当前格式暂不支持");
                break;
        }
    }

}
