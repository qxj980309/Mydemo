package com.example.demo.mock.common.enums;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum DataTypeEnum {
    XML("xml","01"),
    json("json","02" );

    //CI("中平","03");

//    public static final DataTypeEnum JSON = json;
    private final String name;
    private final String code;
    DataTypeEnum(String name,String code){
        this.name = name;
        this.code = code;
    }

    public static String nameToCode(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        for (DataTypeEnum anEnum : DataTypeEnum.values()){
            if (anEnum.name.equals(name.toLowerCase())) {
                return anEnum.code;
            }
        }
        return "";
    }

    public static String codeToName(String dataType){
        DataTypeEnum[] values = DataTypeEnum.values();
        for (DataTypeEnum value : values){
            if (value.getCode().equals(dataType)){
                return value.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
