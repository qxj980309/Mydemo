package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "是否关联公共报文实体类")
public class IsRelatedPO {
    @ApiModelProperty(value = "是否关联公共报文")
    private Boolean switchType;

    @ApiModelProperty(value = "公共报文id")
    private Long id;

    @ApiModelProperty(value = "替换参数")
    private String param;
}
