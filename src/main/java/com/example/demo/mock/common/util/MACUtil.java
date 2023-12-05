package com.example.demo.mock.common.util;

import com.example.demo.mock.entity.po.CompositionRulePO;
import com.example.demo.mock.entity.po.DataElementExcelPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

public class MACUtil {

    private static final Logger log = LoggerFactory.getLogger(MACUtil.class);

    private static final String ENCODE_UTF8 = "UTF-8";

    public static String getValueByMacKey(Map<String, Object> paramsMap, String macKey) {
    }

    public static String compositionData(Map<String, Object> paramsMap, CompositionRulePO compositionRule, List<DataElementExcelPO> dataElement) {
    }

    public static String getMac(Integer algorithmType, String sendSecretKey, String macData) {
    }

    public static String substringMac(String mac, Integer start, Integer end) {
    }

    public static String getMacFullKey(Map<String, Object> resMap, String macKey) {
    }
}
