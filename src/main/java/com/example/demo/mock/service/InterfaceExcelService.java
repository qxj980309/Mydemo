package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.ExcelPO;

import java.util.List;
import java.util.Map;

public interface InterfaceExcelService<T> {
    /*
    *  添加数据
    * @param data 数据
    * @param row 在excel中原有行数
    * */
    void addRow(Map<T, String> data, int row);

    /*
    * 添加表头
    * @param data 表头
    *  @param row 在excel中原有行数
    * */
    void addHeader(Map<T, String> data, int row);

    /*
    * 设置已经处理好了的表头
    * @param mop 表头
    * */
    void setHeaders(Map<String, T> map);

    /*
    * 获取表头和数据
    * @return excelPO
    *
    * */
    ExcelPO getHeaderAndBody() ;

    /*
    * 获取表头
    * @return Map<String, String>
    * */
    Map<String, String> getHeaders();

    /*
    * 获取数据
    *  @return List<Map<String, String>>
    * */
    List<Map<String, String>> getBodyList();

    /*
    * 获取错误信息
    *  @return StringBuilder
    * */
    StringBuilder getErrorMsg() ;
}
