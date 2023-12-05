package com.example.demo.mock.common.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum PlatformCodeEnum {

    FZ_M0001("FZ_M0001","请求参数校验失败"),
    FZ_M0002("FZ-M0002","“MAC校验失败"),
    FZ_M0003("FZ-M0003","访问接口不存在"),
    FZ_M0004("FZ-M0004","未配置路由，通过url不能定位唯一接口"),
    FZ_M0005("FZ-M0005","接口编号不能为空"),
    FZ_M0006("FZ-M0006","接口编号和urL不能同时为空"),
    FZ_M0007("FZ-M0007","接收方系统不存在"),
    FZ_M0008("FZ-M0008","当前请求报文的预处理类型配置错误"),
    FZ_M0009("FZ-M0009","请求报文字段解析异常"),
    FZ_M0010("FZ-M0010","当前请求报文字段解析结构配置错误"),
    FZ_M0011("FZ-M0011","参数位置或参数名配置错误"),
    FZ_M0012("FZ-M0012","参数位置配置错误"),
    FZ_M0013("FZ-M0013","参数名配置错误"),
    FZ_M0014("FZ-M0014","获取指定参数名对应的参数值异常"),
    FZ_M0015("FZ-M0015","当前期望规则配置错误"),
    FZ_M0016("FZ-M0016","http状态码配置错误"),
    FZ_M0017("FZ-M0017","当前签名值拼接位置配置错误"),
    FZ_M0018("FZ-M0018","当前签名算法配置错误"),
    FZ_M0019("FZ-M0019","延迟响应时间配置错误"),
    FZ_M0020("FZ-M0020","报文格式不正确"),
    FZ_M0021("FZ-M0021","当前数据格式配置错误");

    private final String code;
    private final String message;

    PlatformCodeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
}
