package com.example.demo.mock.entity.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "操作管理实体类")
public class OperationTypePO {
    private DelayResponsePO delayResponse;//延迟响应
}
