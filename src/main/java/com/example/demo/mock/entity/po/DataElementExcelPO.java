package com.example.demo.mock.entity.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ExcelTarget("dataElement")
@Data
public class DataElementExcelPO {
    @Excel(name ="序号" ,orderNum = "0")
    @NotBlank(message = "序号不能为空")
    private String serialNo;

    @Excel(name ="英文名称" ,orderNum = "1")
    @NotBlank(message = "英文名称不能为空")
    private String englishName;

    @Excel(name ="中文名称" ,orderNum = "2")
    @NotBlank(message = "中文名称不能为空")
    private String chineseName;

    @Excel(name ="长度" ,orderNum = "3")
    private String length;

    @Excel(name ="说明" ,orderNum = "4")
    private String description;
}
