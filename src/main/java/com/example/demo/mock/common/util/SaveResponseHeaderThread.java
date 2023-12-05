package com.example.demo.mock.common.util;

import com.example.demo.mock.entity.po.ResponseHeaderPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/*
* 使用threadLocal存储和传递 自定义响应头
*
* */
public class SaveResponseHeaderThread {
    private final static Logger log = LoggerFactory.getLogger(SaveDataTypeThread.class);

    ThreadLocal<List<ResponseHeaderPO>> threadLocal;

    /*
     * 创建实例
     * */
    public static SaveResponseHeaderThread getInstance() { return Singleton.sInstance; }

    /*
     *  静态内部类单例模式 - 单例初始化
     * */
    public static class Singleton {
        private static final SaveResponseHeaderThread sInstance = new SaveResponseHeaderThread();
    }


    public void setResponseHeader(List<ResponseHeaderPO> responseHeaderPOList){
       threadLocal.set(responseHeaderPOList);
    }

    public List<ResponseHeaderPO> getResponseHeader() {
        return threadLocal.get();
    }

    public void clear() {
        threadLocal.remove();
    }


}
