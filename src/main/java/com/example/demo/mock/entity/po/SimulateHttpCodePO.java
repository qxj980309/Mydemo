package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "拟http状态码实体类")
public class SimulateHttpCodePO {
    private Boolean switchType;//模拟http状态码开关
    private String statusCode;//状态码
}
