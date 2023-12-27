package com.example.demo.mock.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomResponseException extends RuntimeException {
    private String code;
    private String datatype;

    public CustomResponseException(String code,String message,String datatype){
        super(message);
        this.code = code;
        this.datatype = datatype;
    }
}
