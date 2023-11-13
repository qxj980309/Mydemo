package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "接口表实体类")
@TableName(value = "td_route",autoResultMap = true)
public class RoutePO {
    @ApiModelProperty(value = "逻辑主键")
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty(value = "项目1d")
    @NotNull(message ="工程不能为空")
    @TableField(value = "project_id")
    private Long projectId;

    @ApiModelProperty(value ="请求地址")
    @NotBlank(message ="请求地址不能为空")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value ="接口编号位置")
    @NotBlank(message ="接口编号位置不能为空")
    @TableField(value = "location")
    private String location;

    @ApiModelProperty(value ="接口编号名称")
    @NotBlank(message ="接口编号名称不能为空")
    @TableField(value = "name")
    private String name;
}
