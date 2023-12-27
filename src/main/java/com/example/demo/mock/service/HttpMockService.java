package com.example.demo.mock.service;

import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.entity.UrlEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
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
