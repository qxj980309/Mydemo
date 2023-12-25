package com.example.demo.mock.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IsNeededEnum {
    Must( "y", "是"),
    Not( "N", "否"),
    Condition( "Cn", "条件必输");

    /*
    * 代号
    * */
    private final String code;

    /*
    * 中文名称
    * */
    private final String name;

    private static final Logger log = LoggerFactory.getLogger(IsNeededEnum.class);

    IsNeededEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String toCode(String s) {
        for (IsNeededEnum value : IsNeededEnum.values()){
            if (value.name.equals(s)){
                return value.code;
            }
        }
        log.warn("未匹配到对应值{}",s);
        return s;
    }

    public static boolean isOverRange(String isMust) {
        for (IsNeededEnum value : IsNeededEnum.values()) {
            if (value.code.equals(isMust)){
                return false;
            }
        }
        return true;
    }

}
