package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName(value = "tb_mac_interface",autoResultMap = true)
@ApiModel(value = "MAC 接口关联表 实体类")
public class MacInterfacePO {
    @ApiModelProperty(value = "逻辑主键")
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty(value ="macId")
    @NotBlank(message ="规范ID不能为空")
    private Long macId;

    @ApiModelProperty(value ="接口id")
    @NotBlank(message ="接口id不能为空")
    private Long interfaceId;

    @ApiModelProperty(value ="Mac适用规范")
    @NotBlank(message ="Mac适用规范不能为空")
    private String applicationScope;
}
