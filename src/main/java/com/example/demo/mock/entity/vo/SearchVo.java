package com.example.demo.mock.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "界面搜索项-访问数据统计")
public class SearchVo {
    private String sendSysCode; // 请求方系统号
    private String accSysCode; // 接收方系统号
    private String name; // 接口名称
    private String txCode; // 接口编号
    private String dimension; // 时间维度  nian
//    private String startDate; //开始时间   20231012
//    private String endDate; // 结束时间    20231013
    private String date; //开始时间-开始时间  2022-07-14-2023-10-19
}
