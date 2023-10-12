package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel(description = "访问数据统计表实体类")
public class DataPo {
    private Long id; //主键
    private Long projectId; // 项目编号
    private String sendSysCode; // 请求方系统号
    private String accSysCode; // 接收方系统号
    private String name; // 接口名称
    private String txCode; // 接口编号
    private LocalDate date; // 日期
    private Integer count; //访问次数
}
