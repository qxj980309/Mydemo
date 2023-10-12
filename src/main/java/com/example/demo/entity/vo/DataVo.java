package com.example.demo.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "f访问数据统计")
public class DataVo {
    private Long id; //主键
    private Long projectId; // 项目编号
    private String sendSysCode; // 请求方系统号
    private String accSysCode; // 接收方系统号
    private String name; // 接口名称
    private String txCode; // 接口编号
    private String date; // 日期
    private String dimension; // 时间维度
    private Integer count; //访问次数
}
