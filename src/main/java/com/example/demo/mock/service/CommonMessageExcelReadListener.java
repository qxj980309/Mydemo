package com.example.demo.mock.service;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.fastjson.JSON;
import com.example.demo.common.exception.BizException;
import com.example.demo.mock.entity.po.ExcelPO;
import com.example.demo.mock.service.impl.CommonMessageExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class CommonMessageExcelReadListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger log = LoggerFactory.getLogger(CommonMessageExcelReadListener.class);

    /*
    * -1-无需解折，1-表头，2-数据
    * */
    private int parseType = -1;

    private final InterfaceExcelService<Integer> commonMessageExcel = new CommonMessageExcel<>();

    private final StringBuilder errMsg = new StringBuilder();

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        log.info(JSON.toJSONString(integerStringMap));
        if ("公共报文".equals(integerStringMap.get(0))){
            parseType = 1;
            return;
        }
        switch (parseType){
            case 1:
                parseType++;
                commonMessageExcel.addHeader(integerStringMap, getRowIndex(analysisContext));
                break;
            case 2:
                commonMessageExcel.addRow(integerStringMap, getRowIndex(analysisContext));
                break;
        }
    }

    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof BizException){
            errMsg.append(exception.getMessage()).append(" ");
            // 针对解析过程中抛出异常的模块，不再进行该模块的校验
            parseType = -1;
        } else {
            log.error("文档导入解折异常",exception);
            super.onException(exception, context);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        addErrMsg();
    }

    private int getRowIndex(AnalysisContext analysisContext) {
        return ((XlsxReadSheetHolder) analysisContext.currentReadHolder()).getRowIndex() + 1;
    }

    public StringBuilder getErrHsg() { return errMsg; }

    public ExcelPO getData() { return commonMessageExcel.getHeaderAndBody();}

    private void addErrMsg() {
        if (commonMessageExcel.getErrorMsg().length()>0){
            errMsg.append(commonMessageExcel.getErrorMsg());
        }
    }
}
