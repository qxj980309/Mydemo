package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(description = "项目实体类")
@TableName(value = "td_project",autoResultMap = true)
public class ProjectPo {
    @ApiModelProperty(value = "逻辑主键")
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty(value ="项目名称")
    @NotBlank(message ="项目名称不能为空")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value ="系统号")
    @NotBlank(message ="系统号不能为空")
    @TableField(value = "sys_code")
    private String sysCode; // 请求方系统号

    @ApiModelProperty(value ="项目描述")
    @NotBlank(message ="项目描述不能为空")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(value ="编码格式")
    @NotBlank(message ="编码格式不能为空")
    @TableField(value = "encode_type")
    private String encodeType;
}
