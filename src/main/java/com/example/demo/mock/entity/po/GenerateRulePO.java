package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "MAC生成规则实体类")
public class GenerateRulePO {
    @ApiModelProperty(value ="算法类型")
    @NotBlank(message = "算法类型不能为空")
    private Integer algorithmType;

    @ApiModelProperty(value ="请求方秘钥")
    @NotBlank(message = "请求方秘钥不能为空")
    private String sendSecretKey;

    @ApiModelProperty(value ="接收方秘钥")
    @NotBlank(message = "接收方秘钥不能为空")
    private String accSecretKey;
}
