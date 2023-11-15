package com.example.demo.mock.common.enums;

import lombok.Getter;

@Getter
public enum LocationEnum {
    HEADER("01", "header"),
    BODY( "02",  "body"),
    QUERY("03", "query"),
    PATH("04", "path");

    private final String code;
    private final String desc;

    LocationEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
