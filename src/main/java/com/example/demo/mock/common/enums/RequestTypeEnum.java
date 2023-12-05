package com.example.demo.mock.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum RequestTypeEnum {
    POST("post","01"),
    SOCKET("socket","02"),
    GET("get","03");

    private final String name;
    private final String code;

    RequestTypeEnum(String name,String code){
        this.code = code;
        this.name = name;
    }

    public static String nameToCode(String name){
        if (StringUtils.isBlank(name)){
            return "";
        }
        for (RequestTypeEnum requestTypeEnum : RequestTypeEnum.values()){
            if (requestTypeEnum.name.equals(name.toLowerCase())){
                return requestTypeEnum.name;
            }
        }
        return "";
    }
}
