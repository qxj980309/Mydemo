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

@Getter
@Setter
@ApiModel(value = "接口表实体类")
@TableName(value = "td_interface",autoResultMap = true)
public class InterFacePO {
    @ApiModelProperty(value = "逻辑主键")
    private Long id;
    @ApiModelProperty(value = "项目1d")
    @NotNull(message ="项目不能为空")
    private Long projectId;
    @ApiModelProperty(value ="接口名称")
    @NotBlank(message ="接口名称不能为空")
    private String name;
    @ApiModelProperty(value ="接口编号")
    @NotBlank(message ="接口编号不能为空")
    private String txCode;
    @ApiModelProperty(value ="请求地址")
    private String url;
    @ApiModelProperty(value ="请求方式")
    @NotBlank(message ="请求方式不能为空")
    private String requestType;
    @ApiModelProperty(value ="数据格式")
    @NotBlank(message="数据格式不能为空")
    private String dataType;

    @ApiModelProperty(value ="默认请求")
    private String defaultRequest;

    @ApiModelProperty(value ="默认响应")
    @NotBlank(message ="默认响应不能为空")
    private String defaultResponse;

//    @ApiModelProperty(value ="请求是晋关联公共报文")
//    @Valid
//    @TableField(value = "is_related_request" ,typeHandler = IsRelatedPOTypeHandler.class)
//    private IsRelatedPO isRelatedRequest;
//
//    @ApiModelProperty(value ="请求是晋关联公共报文")
//    @Valid
//    @TableField(value = "is_related_response" ,typeHandler = IsRelatedPOTypeHandler.class)
//    private IsRelatedPO isRelatedResponse;
//
//    @ApiModelProperty(value ="请求报文")
//    @TableField(typeHandler = ExcelTypePOHandler.class)
//    private ExcelPO requestBody;
//
//    @ApiModelProperty(value ="响应报文")
//    @TableField(typeHandler = ExcelTypePOHandler.class)
//    private ExcelPO responseBody;

    @ApiModelProperty(value ="功能描述")
    private String description;

}
