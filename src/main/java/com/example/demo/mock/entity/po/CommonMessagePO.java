package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@ApiModel(description = "tb_common_message 接口表实体类")
@TableName(value = "tb_common_message", autoResultMap = true)
public class CommonMessagePO {
    @ApiModelProperty(value ="逻辑主键")
    private Long id;

    @ApiModelProperty(value ="项目id")
    @NotNull(message ="项目不能为空")
    private Long projectId;

    @ApiModelProperty(value ="公共报文名称")
    @NotNull(message ="公共报文名称不能为空")
    private String name;

    @ApiModelProperty(value ="公共报文")
    //@TableField(typeHandler = ExcelPOTypeHandler.class)
    private ExcelPO commonMessage;

    @ApiModelProperty(value ="功能描述")
    private String description;

    @ApiModelProperty(value ="是否关联公共报文")
    private String switchType;

}

