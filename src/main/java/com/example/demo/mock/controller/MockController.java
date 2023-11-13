package com.example.demo.mock.controller;

import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.common.entity.UrlEntity;
import com.example.demo.mock.service.HttpMockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class MockController {
    @Resource
    private HttpMockService httpMockService;

    private static final Logger log = LoggerFactory.getLogger(MockController.class);

    private String mock(HttpServletRequest request , HttpServletResponse response, @RequestBody String body){
        UrlEntity urlEntity = parseUrl(request.getRequestURI());
        Map<String,String> headers = getHeaders(request);
        MockResponse mockRes = httpMockService.mock(urlEntity,headers,body);
        addStatus(response,mockRes.getStatus());
        addErrMsg(response,mockRes.getErrMsg());
        return mockRes.getBody();
    }

    private String mock(HttpServletRequest request , HttpServletResponse response){
        String params = request.getQueryString();
        UrlEntity urlEntity = parseUrl(request.getRequestURI());
        Map<String,String> headers = getHeaders(request);
        String characterEncoding = request.getCharacterEncoding();
        MockResponse mockRes = httpMockService.mock(urlEntity,headers,params,characterEncoding);
        addStatus(response,mockRes.getStatus());
        addErrMsg(response,mockRes.getErrMsg());
        return mockRes.getBody();
    }


    private UrlEntity parseUrl(String url) {
        //url分成4部分
        String[] split = StringUtils.split(url,"/",4);
        Assert.isTrue(split.length==4,()-> String.valueOf(new NullPointerException("url格式错误")));
        return new UrlEntity(split[1],split[2],"/"+split[3]);
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        HashMap<String,String> hashMap = new HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            hashMap.put(name,value);
        }
        return hashMap;
    }

    private void addStatus(HttpServletResponse response, Integer status) {
        if(null == status){
            return;
        }
        response.setStatus(status);
    }


    private void addErrMsg(HttpServletResponse response, String errMsg) {
        if (StringUtils.isBlank(errMsg)){
            return;
        }
        try{
            response.setCharacterEncoding("UTF-8");
            response.setHeader("errMsf",errMsg);
            response.setStatus(400);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
