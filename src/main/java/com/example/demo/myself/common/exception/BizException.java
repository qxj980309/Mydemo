package com.example.demo.myself.common.exception;

import java.io.Serializable;

public class BizException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public BizException(String errorMessage){
        super(errorMessage);
        this.errorCode = "-1";
    }

    public BizException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
