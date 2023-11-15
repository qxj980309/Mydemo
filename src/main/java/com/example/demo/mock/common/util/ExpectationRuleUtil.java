package com.example.demo.mock.common.util;

import com.example.demo.mock.common.enums.ExpectationRuleEnum;
import org.apache.commons.lang3.StringUtils;

public class ExpectationRuleUtil {

    private ExpectationRuleUtil() {}

    /*
    * 判断期望是否匹配
    *  @param condition 条件
    *  @param source当前请求中的值
    *  @param target配置中的目标值
    *  @return true if matching, else false
    * */
    public static Boolean isRuleMatching(String condition,String source,String target){
        switch (ExpectationRuleEnum.getByCode(condition)){
            case RULE_01:
                return equals(source, target);
            case RULE_02:
                return notEquals(source, target);
            case RULE_03:
                return isOneOf(source, target);
            case RULE_04:
                return isNotOneOf(source, target);
            case RULE_05:
                return contains(source, target);
            case RULE_06:
                return notContains(source, target);
            case RULE_07:
                return exist(source);
            case RULE_08:
                return notExist(source);
            default:
                throw new RuntimeException("不支持的期望规则");
        }
    }

    private static boolean equals(String source, String target) { return target.equals(source); }

    private static boolean notEquals(String source, String target) { return !target.equals(source); }

    private static boolean isOneOf(String source, String target) {
        String[] strings = StringUtils.split(
                StringUtils.replace(target,  ", ", ", "),  ", ");
        for (String string : strings) {
            if (string.equals(source)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNotOneOf(String source, String target) { return !isOneOf(source, target); }

    private static boolean contains(String source, String target) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        return source.contains(target);
    }

    private static boolean notContains(String source, String target) { return !contains(source, target); }

    private static boolean exist(String source){ return StringUtils.isNotBlank(source); }

    private static boolean notExist(String source) { return StringUtils.isBlank(source); }

}
