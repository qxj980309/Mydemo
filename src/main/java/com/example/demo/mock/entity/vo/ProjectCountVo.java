package com.example.demo.mock.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "项目实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectCountVo {
    @ApiModelProperty(value ="项目名称")
    private List<String> name;
    @ApiModelProperty(value = "接口总数")
    private List<Integer> interfaceCount;
    @ApiModelProperty(value = "案例总数")
    private List<Integer> caseCount;

    @ApiModelProperty(value ="项目名称")
    private String name1;
    @ApiModelProperty(value = "接口总数")
    private Integer interfaceCount1;
    @ApiModelProperty(value = "案例总数")
    private Integer caseCount1;

}
