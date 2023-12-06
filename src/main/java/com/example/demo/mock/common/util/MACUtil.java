package com.example.demo.mock.common.util;

import com.example.demo.mock.common.Constants.Constants;
import com.example.demo.mock.entity.po.CompositionRulePO;
import com.example.demo.mock.entity.po.DataElementExcelPO;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MACUtil {

    private static final Logger log = LoggerFactory.getLogger(MACUtil.class);

    private static final String ENCODE_UTF8 = "UTF-8";

    /*
    * 获取MAC值
    *
    *  @param algorithmType MAC采用的算法类型
    *  @param sendSecretKey 秘钥ID
    *  @param data 数据
    * @return
    * */
    public static String getMac(int algorithmType, String sendSecretKey, String macData) {
        return doGetMAC(algorithmType, sendSecretKey, macData);
    }

    /*
     * 验证MAC值
     *
     *  @param algorithmType MAC采用的算法类型
     *  @param sendSecretKey 秘钥ID
     *  @param data mac数据
     *  @return
     * */
    public static boolean checkMac(int algorithmType, String sendSecretKey,String orgDate ,String macData) {
        return doCheckMAC(algorithmType, sendSecretKey,orgDate, macData);
    }


    /*
    *获取MAC待加密字符申
    *
    * @param compositionRulePO  MAC央数据元的构成规购
    * @param  dataElement MAC数据元
    * @return
     * */
    public static String compositionData(Map<String, Object> paramsMap, CompositionRulePO compositionRulePO, List<DataElementExcelPO> dataElement) {
        // 转平铺
        Map<String, Object> flattenedParamsMap = new HashMap<>();
        flattenMap( "", paramsMap, flattenedParamsMap);

        List<String> macDataList = new ArrayList<>();
        for (DataElementExcelPO po: dataElement){
            for (Map.Entry<String, Object> entry : flattenedParamsMap.entrySet()) {
                String key = entry.getKey();
                String lastKey = key.substring(key.lastIndexOf('.')+1);
                if (lastKey.equals(po.getEnglishName())){
                    macDataList.add(entry.getValue().toString());
                    break;
                }
            }
        }
        return spliceDate(macDataList, compositionRulePO);
    }

    /*
    * 响应报文的map中取macKey带层级
    * @param  resMap响应报文
    * @param  macKey参数名
    * @return mac值
    * */
    public static String getMacFullKey(Map<String, Object> resMap, String macKey) {
        for(Map.Entry<String, Object> entry : resMap.entrySet()){
            String currentKey = entry.getKey();
            Object value = entry.getValue();

            if(currentKey.equals(macKey)){
                return currentKey;
            }

            if(value instanceof Map){
                String nestedPath = getMacFullKey((Map<String, Object>) value, macKey);
                if(nestedPath != null){
                    return currentKey + "." + nestedPath;
                }
            }
        }
        // 响应报文没有当前mac字段,返回null
        return null;
    }

    /*
    * 过请求报文中的mac字段参数名(不带层级)，获mac值
    *
    *  @param paramsMap
    *  @param macKey
     * @return mac值
    * */
    public static String getValueByMacKey(Map<String, Object> paramsMap, String macKey) {
        for(Map.Entry<String, Object> entry : paramsMap.entrySet()){
            String currentKey = entry.getKey();
            Object value = entry.getValue();

            if(currentKey.equals(macKey)){
                return value.toString();
            }

            if(value instanceof Map){
                String result = getValueByMacKey((Map<String, Object>) value, macKey);
                if(result != null){
                    return result;
                }
            }
        }
        // 响应报文没有当前mac字段,返回null
        return null;
    }

    /*
     * MAC值截取
     *
     * @param mac mac数据
     * @param start 起始位置
     * @param end 结束位置
     *  return
     * */
    public static String substringMac(String mac, Integer start, Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = mac.length();
        }
        return mac.substring(start, end);
    }

    private static String spliceDate(List<String> macDataList, CompositionRulePO compositionRulePO) {
        String regex = new String();
        if(compositionRulePO.getReservedChar() != null && compositionRulePO.getReservedChar().length()> 0){
            regex = reservedCharRegex(compositionRulePO.getReservedChar());
        }
        List<String> spaceProcessList = new ArrayList<>();
        if(compositionRulePO.getSpaceProcess() != null && compositionRulePO.getSpaceProcess().length() > 0){
            spaceProcessList = StrToArr(compositionRulePO.getSpaceProcess());
        }
        String resultData = new String();
        Integer falseTrim = 0; //判断是否制除首尾空格的标志
        for (String macData : macDataList) {
            // 保留字符
            if(regex.length() >0){
                macData = macData.replaceAll(regex, "");
            }
            // 空格处理
            for(String spaceProcess : spaceProcessList){
                switch (spaceProcess) {
                    case Constants.SPACE_PROCESS_FIRST_LAST:
                        macData = macData.trim();
                        falseTrim = 1;
                        break;
                    case Constants.SPACE_PROCESS_CONTINUOUS:
                        macData = macData.replaceAll("\\s+"," ");
                        break;
                    default:
                        log.info("MAC块数据元的构成规则异常，未匹配的空格处理{}",spaceProcess);
                }
            }
            // 数据元与数据元之间的拼接方式
            switch (compositionRulePO.getSpliceType()) {
                case Constants.SPLICE_TYPE_COMPACT:
                    resultData += macData;
                    break;
                case Constants.SPLICE_TYPE_SPACE:
                    resultData += macData + " ";
                    break;
                default:
                    log.info("MAC块数据元的构成规则异常，未匹配的数据元与数据元之间的拼摇方式{}",compositionRulePO.getSpliceType());
            }
        }
        if(falseTrim.equals(1)){
            resultData = resultData.trim();
        }
        return resultData;
    }

    /*
    *生成保留字符的正则字符中
    * @param str 保字符
    *  @return 正则字符中
    * */
    private static String reservedCharRegex(String str){
        List<String> reservedCharList = StrToArr(str);
        String regex = new String();
        for(String reservedChar : reservedCharList){
            switch (reservedChar) {
                case Constants.RESERVED_CHAR_LETTER:
                    regex =regex.concat("a-zA-Z");
                    break;
                case Constants.RESERVED_CHAR_NUMBER:
                    regex = regex.concat("0-9");
                    break;
                case Constants.RESERVED_CHAR_SPACE:
                    regex = regex.concat(" ");
                    break;
                case Constants.RESERVED_CHAR_COMMA:
                    regex = regex.concat(",");
                    break;
                case Constants.RESERVED_CHAR_NEGATIVE:
                    regex = regex.concat("\\.");
                    break;
                case Constants.RESERVED_CHAR_POSITIVE:
                    regex = regex.concat("+");
                    break;
                case Constants.RESERVED_CHAR_SPOT:
                    regex = regex.concat(".");
                    break;
                default:
                    log.info("MAC块数据元的构成规则异常，未匹配的保留字符{}",reservedChar);
            }
        }
        if(regex.length() == 0){
            return "";
        }
        return "[^".concat(regex).concat( "]");
    }

    /*
    *将字符申数组转成数组
    * @param str 字符串数组
    * @return list
    * */
    private static List<String> StrToArr(String str) {
        List<String> arr = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i = 0; i < jsonArray.length(); i++){
                arr.add(jsonArray.getString(1));
            }
        }catch (Exception e){
            log.error("JSONArray结构解折异常",e);
        }
        return arr;
    }

    /*
    *  将 Map<String, Object>结构转成平铺的 Map<String, Object>结构
    * @param prefix
    * @param paramsMap
    * @return flattenedParamsMap
    * */
    private static void flattenMap(String prefix, Map<String, Object> paramsMap, Map<String, Object> flattenedParamsMap) {
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String flattenedKey = prefix.isEmpty() ? key : prefix + "." + key;
            if (value instanceof Map) {
                flattenMap(flattenedKey,(Map<String, Object>) value, flattenedParamsMap);
            } else {
                flattenedParamsMap.put(flattenedKey, value);
            }
        }
    }

    /*
    * 生成MAC
    *
    * @param algorithmType MAC果用的算法类型
    * @param sendSecretKey 对称密ID
    * @param macData 用户数据
    * @return
    * */
    private static String doGetMAC(int algorithmType, String sendSecretKey, String macData) {
        int ry = 0;
//        psbc_csp_api csp_api = new psbc_csp_api();
//        psbc_csp_handle pCspHandle = new psbc_csp_handle();
        log.info("doGetMAC 获取MAC发送请求{}",macData);
        try {
            // 初始化值
//            ry = csp_api.PSBC_Connect(pCspHandle);
//            if (ry != csp_api.PSBC_Connect.DTCSP_SUCCSEE){
//                log.error("密管平台连接错误，错误码为: ",csp_api.PSBC_GetErrCode());
//            }
            //调用方法 MAC生成
//            byte[] mac = csp_api.p58c.GenMac(pCspHandle, algorithmType, sendSecretKey.getBytes(ENCODE_UTF8), macData.getBytes(ENCODE_UTF8));
            if (macData == null) {
//                log.error("生成 MAC失败，Error rv = {}",csp_api.PSBC_GetErrCode());
            }
//            String retMac = new String(mac,ENCODE_UTF8);
//            log.info("doGetHAC 获取MAC完成, rv={} mac=[{}]", csp-api.psBc.GetErrCode(),retMac);
//            return retMac;
        }catch (Exception e){
            log.error("生成MAC失败",e);
        }finally {
            // 确保无论是否发生异常都要关闭
//            ry = csp_api.PsBc_Disconnect(pCspHandle);
            if (ry != 0){
//                log.error("关闭密管平台连换异常，镇误码为: {}",csp_ap1,PSBC_GetErrCode());
            }
        }
        return null;
    }

    /*
    *MAC 校验
    *
    * @param algorithmType MAC果用的算法类型
    * @param sendSecretKey 对称密ID
    * @param macData 用户数据
    * @return
    * */
    private static boolean doCheckMAC(int algorithmType, String sendSecretKey, String userData,  String macData) {
        int ry = 0;
//        psbc_csp_api csp_api = new psbc_csp_api();
//        psbc_csp_handle pCspHandle = new psbc_csp_handle();
        log.info("doCheckMAC 校验MAC发送请求{}",macData);
        try {
            // 初始化值
//            ry = csp_api.PSBC_Connect(pCspHandle);
//            if (ry != csp_api.PSBC_Connect.DTCSP_SUCCSEE){
//                log.error("密管平台连接错误，错误码为: ",csp_api.PSBC_GetErrCode());
//            }
            // 调用方法，校验MAC
//            boolean check = csp_api.PSBc_Verifymac(pCspHandle,algorithmType,sendSecretKey.getBytes(ENCODE_UTF8),
//                    userData.getBytes(ENCODE_UTF8),macData.getBytes(ENCODE_UTF8));
//            log.info("doCheckMAC 验证MAC完成，rv=0",csp_api.PSBC_GetErrCode());
//            return check;
        }catch (Exception e){
            log.error("校验MAC失败",e);
        }finally {
            // 确保无论是否发生异常都要关闭
//            ry = csp_api.PsBc_Disconnect(pCspHandle);
            if (ry != 0){
//                log.error("关闭密管平台连换异常，镇误码为: {}",csp_ap1,PSBC_GetErrCode());
            }
        }
        return false;
    }

}
