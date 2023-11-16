package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(value = "请求报文预处理实体类")
public class PretreatmentPO {
    @ApiModelProperty(value ="预处理类型")
    @NotBlank(message ="预处理类型不能为空")
    private String type;

    @ApiModelProperty(value ="分隔符")
    @NotBlank(message ="分隔符不能为空")
    private String delimiter;
}
