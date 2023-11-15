package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "excel实体类")
@NoArgsConstructor
@AllArgsConstructor
public class ExcelPO {
    @ApiModelProperty(value = "表头")
    private Map<String,String> headers;

    @ApiModelProperty(value = "数据")
    private List<Map<String,String>> bodyList;
}
