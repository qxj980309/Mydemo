package com.example.demo.mock.common.util;

import com.example.demo.mock.entity.po.ResponseHeaderPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SaveDataTypeThread {
    private final static Logger log = LoggerFactory.getLogger(SaveDataTypeThread.class);

    ThreadLocal<String> threadLocal;

    /*
    * 创建实例
    * */
    public static SaveDataTypeThread getInstance() { return SaveDataTypeThread.Singleton.sInstance; }

    /*
    *  静态内部类单例模式 - 单例初始化
    * */
    public static class Singleton {
        private static final SaveDataTypeThread sInstance = new SaveDataTypeThread();
    }

    public void setDataType(String context){
        if("01".equals(context) || "02".equals(context)){
            threadLocal.set(context);
        } else {
            threadLocal.set(CustomResponseUtil.convertDatatype(context));
        }
    }

    public String getDataType() {
        return threadLocal.get();
    }

    public void clear() {
        threadLocal.remove();
    }
}
