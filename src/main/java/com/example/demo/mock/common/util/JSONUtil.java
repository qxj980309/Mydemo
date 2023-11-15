package com.example.demo.mock.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class JSONUtil {
    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    private JSONUtil() {}

    /*
    * 从jsonObject中取key对应的value
    * 如果jsonObject中存在数组，通过index取对应某个的值
    *
    *  @param jsonObject 请求
    *  @param keys存在层级，例如msgHeader.signSN，idList.0.id
    *  @return valve 取不到返回null
    *
    * */
    public static String getValue(Map<String, Object> jsonObject, String keys) {
        Assert.hasText(keys,"key不能为空");
        Object tmp = jsonObject;
        String[] keyList = getKeyList(keys);
        for (String key : keyList) {
            if (null == tmp) {
                return null;
            }
            if (StringUtils.isBlank(key)){
                throw new RuntimeException("参数配置错误:".concat(keys));
            }
            try {
                tmp = getObject(tmp, key);
            }catch (Exception e){
                log.warn("从jsonobject中获取指定key的值异常",e);
                return null;
            }
        }
        return null == tmp ? null : String.valueOf(tmp);
    }

    public static Object getObject(Object tmp, String key) {
        if (StringUtils.isNumeric(key)) {
            tmp = ((List<?>) tmp).get(Integer.parseInt(key));
        } else {
            tmp = ((Map<?,?>) tmp).get(key);
        }
        return tmp;
    }

    public static String[] getKeyList(String key) {
        return key.split(" \\.");
    }

}
