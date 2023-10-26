package com.example.demo.mock.common.util;

import javafx.scene.chart.XYChart;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ColumnarModel  {

    public List<String> legend = new ArrayList<String>();//数据分组
    public List<String> category = new ArrayList<String>();//横坐标
    public List<XYChart.Series> series = new ArrayList<XYChart.Series>();//纵坐标


    public ColumnarModel(List<String> legendList, List<String> categoryList, List<XYChart.Series> seriesList) {
        super();
        this.legend = legendList;
        this.category = categoryList;
        this.series = seriesList;
    }


//    /**
//     * 顶部数据
//     */
//    private List<String> legendData;
//    /**
//     * 横坐标数据
//     */
//    public List<String> xData;
//    /**
//     * 柱状图上的具体数据
//     */
//    public List<String> seriesData;
//
//    public ColumnarModel(List<String> legendData, List<String> xData, List<String> seriesData) {
//        super();
//        this.legendData = legendData;
//        this.xData = xData;
//        this.seriesData = seriesData;
//    }


}
