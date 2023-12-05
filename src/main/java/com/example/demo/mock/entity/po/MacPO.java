package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@TableName(value = "tb_mac",autoResultMap = true)
@ApiModel(description = "MAC 配置表实体类")
public class MacPO {
    @ApiModelProperty(value ="逻辑主键")
    private Long id;

    @ApiModelProperty(value = "项目id")
    @NotBlank(message ="项目id不能为空")
    private Long projectId;

    @ApiModelProperty(value = "MAC规范名称")
    @NotBlank(message ="MAC规范名称不能为空")
    private String name;

    @ApiModelProperty(value = "功能描述")
    private String description;

    @ApiModelProperty(value = "“MAC数据元")
//    @TableField(typeHandler = DataElementExcelPOTypeHandler.class)
    private List<DataElementExcelPO> dataElement;

    @ApiModelProperty(value = "MAC生成规则")
    @Valid
//    @TableField(value = "generate_rule", typeHandler = GenerateRulePoTypeHandler.class)
    private GenerateRulePO generateRule;

    @ApiModelProperty(value = "MAC块数据元的构成规则")
    @Valid
//    @TableField(value = "composition_rule", typeHandler = CompositionRulePOTypeHandler.class)
    private CompositionRulePO compositionRule;

    @ApiModelProperty(value = "MAC值截取")
    @Valid
//    @TableField(value = "intercept", typeHandler = InterceptPoTypeHandler.class)
    private InterceptPO intercept;

    @ApiModelProperty(value = "报文中HAC字段参数名")
    @NotBlank(message ="报文中HAC字段参数名")
    private String macKey;

}
