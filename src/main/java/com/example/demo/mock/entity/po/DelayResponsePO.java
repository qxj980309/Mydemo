package com.example.demo.mock.entity.po;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "延迟响应实体类")
public class DelayResponsePO {
    private Boolean switchType;//延迟开关
    private Integer delayTime;//延迟时间
}
