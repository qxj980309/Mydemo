package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "响应报文生成签名实体类")
public class SignaturePO {
    @ApiModelProperty(value ="签名算法")
    @NotBlank(message = "签名算法不能为空")
    private String algorithm;

    @ApiModelProperty(value ="秘钥")
    @NotBlank(message = "秘钥不能为空")
    private String secretKey;

    @ApiModelProperty(value ="拼接位置")
    @NotBlank(message = "拼接位置不能为空")
    private String location;

    @ApiModelProperty(value ="需要替换的参数名")
    private String name;
}
