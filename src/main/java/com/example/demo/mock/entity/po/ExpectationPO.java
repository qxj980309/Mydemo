package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "期望实体类")
public class ExpectationPO {
    @ApiModelProperty(value = "参数位置")
    @NotBlank(message = "期望请求条件-参数位置不能为空")
    private String location;

    @ApiModelProperty(value = "参数名")
    @NotBlank(message = "期望请求条件-参数名不能为空")
    private String key;

    @ApiModelProperty(value = "条件")
    @NotBlank(message = "期望请求条件-条件不能为空")
    private String condition;

    @ApiModelProperty(value = "参数值")
    private String value;
}
