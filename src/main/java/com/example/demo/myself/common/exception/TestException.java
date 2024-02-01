package com.example.demo.myself.common.exception;

import com.example.demo.myself.common.result.ResultCodeEnum;

/*
* 测试自定义异常类
* 需要继承运行时异常RuntimeException
* */
public class TestException extends RuntimeException {
    private Integer code;

    public TestException(ResultCodeEnum resultCodeEnum) {
        // 调用父类的方法添加信息
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
