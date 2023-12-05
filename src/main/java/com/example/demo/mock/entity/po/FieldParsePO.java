package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "请求报文字段解析实体类")
public class FieldParsePO {
    @ApiModelProperty(value ="参数名")
    @NotBlank(message ="参数名不能为空")
    private String name;

    @ApiModelProperty(value ="解析结构")
    @NotBlank(message ="解析结构不能为空")
    private String structure;
}
