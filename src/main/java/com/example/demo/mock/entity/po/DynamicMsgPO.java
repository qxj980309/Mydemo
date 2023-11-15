package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(description = "动态报文PO")
public class DynamicMsgPO {
    @ApiModelProperty(value = "动态报文key" , required = true)
    @NotBlank(message = "响应配置-需替换参数名不能为空")
    private String key;

    @ApiModelProperty(value = "规则" , required = true)
    @NotBlank(message = "响应配置-动态响应规则不能为空")
    private String rule;

    @ApiModelProperty(value = "动态报文key")
    private String param;

}
