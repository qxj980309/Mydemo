package com.example.demo.mock.service;

import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.entity.po.CommonMessagePO;
import com.example.demo.mock.entity.po.InterfacePO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CommonMockService {
    /*
    * 进入Mock方法
    * projectID 工程id
    * txCode 接口编号
    * url 请求url
    * headers http请求头
    * paramsMap 请求参数
    * */
//    MockResponse mock(Long projectId, String txCode, String url, Map<String ,String> headers, Map<String ,Object> paramsMap);

    /*
     * 通用Mock方法
     * projectID 工程id
     * txCode 接口编号
     * headers http请求头
     * paramsMap 请求参数
     * */
//    MockResponse mock(Long projectId, String txCode, Map<String ,Object> paramsMap);
    MockResponse mock(InterfacePO interfacePO, Map<String ,String> headers, Map<String ,Object> paramsMap);

    /*
     * 当前接口关联公共报文
     * interfacePO 接口信息
     * return 关联公共报文信息
     * */
    CommonMessagePO getCommonMessage(InterfacePO interFacePO);
}
