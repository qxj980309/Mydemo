package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResponseHeaderPO {
    @ApiModelProperty(value ="响应头名称")
    @NotBlank(message ="响应头名称不能为空")
    private String headerName;


    @ApiModelProperty(value ="响应头值")
    @NotBlank(message ="响应头值不能为空")
    private String headerValue;

}
