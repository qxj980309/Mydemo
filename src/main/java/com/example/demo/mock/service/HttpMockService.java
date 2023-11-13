package com.example.demo.mock.service;

import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.entity.UrlEntity;

import java.util.Map;

public interface HttpMockService {

    /*
    * post 请求
    * */
    MockResponse mock(UrlEntity urlEntity , Map<String ,String > headers,String body);

    /*
    * get  请求
    * */
    MockResponse mock(UrlEntity urlEntity , Map<String ,String > headers,String body,String characterEncoding);
}
