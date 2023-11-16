package com.example.demo.mock.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ReplaceResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(ReplaceResponseUtil.class);

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
}
