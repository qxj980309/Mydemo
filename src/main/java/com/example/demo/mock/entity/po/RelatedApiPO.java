package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName(value = "tb_related_api",autoResultMap = true)
@ApiModel(description = "接口关联表实体类")
public class RelatedApiPO {
    @ApiModelProperty(value = "逻辑主键")
    private Long id;

    @ApiModelProperty(value = "多案例id")
    private Long caseId;

    @ApiModelProperty(value = "关联标识名")
    @NotBlank(message = "关联标识名不能为空")
    private String key;

    @ApiModelProperty(value = "请求参数名")
    @NotBlank(message = "请求参数名不能为空")
    private String name;

    @ApiModelProperty(value = "请求参数值")
    @NotBlank(message = "请求参数值不能为空")
    private String value;

}
