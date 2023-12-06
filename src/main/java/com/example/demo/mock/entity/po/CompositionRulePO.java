package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "MAC 块数据元的构成规则实体类")
public class CompositionRulePO {
    @ApiModelProperty(value ="数据元之间拼接方式")
    @NotBlank(message = "数据元之间拼接方式不能为空")
    private String spliceType;

    @ApiModelProperty(value ="保留字符")
    @NotBlank(message = "保留字符不能为空")
    private String reservedChar;

    @ApiModelProperty(value ="空格处理")
    @NotBlank(message = "空格处理不能为空")
    private String spaceProcess;

}
