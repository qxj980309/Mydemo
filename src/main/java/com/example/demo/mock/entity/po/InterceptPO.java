package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "MAC值截取实体类")
public class InterceptPO {
    @ApiModelProperty(value ="起始位置")
    @NotBlank(message = "起始位置不能为空")
    private Integer start;

    @ApiModelProperty(value ="结束位置")
    @NotBlank(message = "结束位置不能为空")
    private Integer end;
}
