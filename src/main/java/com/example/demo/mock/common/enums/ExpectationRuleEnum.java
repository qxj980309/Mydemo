package com.example.demo.mock.common.enums;

import lombok.Getter;

@Getter
public enum ExpectationRuleEnum {
    RULE_01("01","等于"),
    RULE_02("01","不等于"),
    RULE_03("01","属于"),
    RULE_04("01","不属于"),
    RULE_05("01","包含"),
    RULE_06("01","不包含"),
    RULE_07("01","存在"),
    RULE_08("01","不存在");

    private final String code;
    private final String desc;

    ExpectationRuleEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static ExpectationRuleEnum getByCode(String code){
        for(ExpectationRuleEnum ruleEnum : ExpectationRuleEnum.values()){
            if (ruleEnum.getCode().equals(code)){
                return ruleEnum;
            }
        }
        throw new RuntimeException("不支持的期望规则");
    }
}
