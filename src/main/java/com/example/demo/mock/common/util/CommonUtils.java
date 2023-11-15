package com.example.demo.mock.common.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.example.demo.mock.common.enums.DataTypeEnum;
import com.example.demo.mock.common.enums.LocationEnum;
import com.example.demo.mock.entity.po.DelayResponsePO;
import com.example.demo.mock.entity.po.OperationTypePO;
import com.example.demo.mock.entity.po.RoutePO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    private final static Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {}

    public static String splicing(Object... strings) {
        if (strings.length < 2) {
            throw new RuntimeException("拼接错误: 参数异常");
        }
        StringBuilder ret = new StringBuilder();
        for (Object string : strings) {
            ret.append(string).append("-");
        }
        return ret.deleteCharAt(ret.length() - 1).toString();
    }
    /*
    *解析body，将json或者xml转换为Map<String，bject>
    *法
    */
    public static Map<String, Object> parseBody(String contentType, String body) {
        try{
            if (null == contentType || contentType.contains(DataTypeEnum.json.getName())){
                // 默认为json格式
                return JSON.parseObject(body);
            } else if (contentType.contains(DataTypeEnum.XML.getName())) {
                JSONObject jsonobject = XML.toJSONObject(body);
                Object root = jsonobject.get("R00T");
                Object root1 = jsonobject.get("root");
                if (root == null && root1 == null){
                    return jsonobject;
                }else{
                    Map<String,Object> convertRoot;
                    convertRoot = root == null ? ( Map<String,Object>)root1 : ( Map<String,Object>)root;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("报文解析异常");
        }
        throw new RuntimeException("该报文格式暂无法解折");
    }


    /*
    *从请求中解析接口编号
    * */
    public static String getTxCode(RoutePO routePO, Map<String, String> headers,Map<String, Object> body){
        //未配置路由规则，无需使用接口斗，直接通过url定位接口
        if (routePO == null) {
            return null;
        }
        return getValue(routePO.getLocation(), routePO.getName(), headers, body);
    }

    /*
    *  @param bodyMap请求报文
    * @param paraName 参数名
    * @return 参数名内容
    * */
    public static String getTxCode(Map<String, Object>  bodyMap, String paraName) {
        if (StringUtils.isBlank(paraName)) {
            return null;
        }
        return JSONUtil.getValue(bodyMap, paraName);
    }

    public static String getValue(String location, String key, Map<String, String> headers, Map<String, Object> body){
        if (StringUtils.isAnyBlank(location, key)) {
            log.error("参数配置错误:".concat(location).concat(",").concat(key));
            return null;
        }
        if (LocationEnum.HEADER.getCode().equals(location)){
            // 从header中获取
            return headers.get(key);
        }
        if (LocationEnum.BODY.getCode().equals(location)  || LocationEnum.QUERY.getCode().equals(location)){
            //从body中获取
            return JSONUtil.getValue(body, key);
        }else {
            throw new RuntimeException("不支持的参数位置:".concat(location));
        }
    }

    public static void postAction(OperationTypePO operationTypePo) {
        if (null == operationTypePo) {
            return;
        }
        // 后续通过多线程调用后置操作
        delayResponse(operationTypePo.getDelayResponse());
    }

    private static void delayResponse(DelayResponsePO po) {
        if (null == po || !po.getSwitchType()) {
            return;
        }
        if (null == po.getDelayTime()) {
            log.error("延时时间配置错误");
            return;
        }
        try {
            Thread.sleep(po.getDelayTime());
        }catch (InterruptedException e){
            e.printStackTrace();
            log.error("延时功能异常interruptedException;",e);
        }
    }

    /*
    * 将get请求参数 JSON -> Map<String,String>
    * */
    public static Map<String, Object> getParams(String params, String characterEncoding) {
        String paramsDecode = decode(params, characterEncoding);
        String regex ="([^=&?]+)=([=&?]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paramsDecode);
        Map<String, Object> map = new LinkedHashMap<>();
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    private static String decode(String content, String characterEncoding) {
        try {
            return URLDecoder.decode(content, characterEncoding);
        }catch (Exception e){
            log.error("GET请求URL解码异常",e);
            throw new RuntimeException("GET请求URL解码异常");
        }
    }

}
