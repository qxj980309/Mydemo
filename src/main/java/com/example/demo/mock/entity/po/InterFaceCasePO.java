package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "接口案例实体类")
@TableName(value = "td_interfaceCase",autoResultMap = true)
public class InterFaceCasePO{
    @ApiModelProperty(value = "逻辑主键")
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty(value = "接口1d")
    @NotNull(message ="接口不能为空")
    @TableField(value = "interface_id")
    private Long interfaceId;

    @ApiModelProperty(value = "规则1d")
    @TableField(value = "rule_id")
    private Long ruleId;

    @ApiModelProperty(value ="接口名称")
    @NotBlank(message ="接口名称不能为空")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value ="期望响应")
    @NotBlank(message ="期望响应不能为空")
    @TableField(value = "response")
    private String response;


//    @ApiModelProperty(value = "期望")
////    @TableField(value = "expectation", typeHandler = ExpectListTypeHandler.class)
//    @Valid
//    private List<ExpectationPO> expectationPOList;


}