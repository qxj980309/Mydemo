package com.example.demo.mock.common.filter;

import com.example.demo.mock.common.util.SaveResponseHeaderThread;
import com.example.demo.mock.common.wrapper.RequestWrapper;
import com.example.demo.mock.entity.po.ResponseHeaderPO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        log.info("----------------------------------交易开始------------------------------------");
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            RequestWrapper requestWrapper = new RequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            handleRequest(requestWrapper, responseWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
            handleResponse(requestWrapper, responseWrapper);
        }catch (Exception e){
            log.error("请求异常",e);
        }
        log.info("耗时：{}",System.currentTimeMillis()-startTime);
        log.info("----------------------------------交易结束------------------------------------");
    }

    private void handleRequest(RequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws UnsupportedEncodingException {
        requestWrapper.setCharacterEncoding(StandardCharsets.UTF_8.name());
        recordRequestLog(requestWrapper);
    }

    private void recordRequestLog(RequestWrapper requestWrapper) {
        if(isMockInterface(requestWrapper.getRequestURI())){
            log.info("Request header={}",getHeaders(requestWrapper));
            log.info("Request content={}",requestWrapper.getContentType());
        }
    }

    private Map<String,String> getHeaders(HttpServletRequest request) {
        HashMap<String,String> hashMap = new HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            hashMap.put(name, value);
        }
        return hashMap;
    }

    private void handleResponse(RequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws IOException {
        if(isMockInterface(requestWrapper.getRequestURI())){
            String data = byteToString(responseWrapper.getContentAsByteArray(),responseWrapper.getCharacterEncoding());
            log.info("Request content={}",data);

            // 根据请求中的Charset响应数据，默认UTF-8
            Charset charset = null != requestWrapper.getCharset() ? requestWrapper.getCharset() : StandardCharsets.UTF_8;
            byte[] newBytes = data.getBytes(charset);
            // 设置响应头
            HttpServletResponse response = (HttpServletResponse) responseWrapper.getResponse();
            List<ResponseHeaderPO> responseHeaderList = SaveResponseHeaderThread.getInstance().getResponseHeader();
            if (responseHeaderList == null || responseHeaderList.size() ==0){
                String contentType = requestWrapper.getContentType();
                response.setContentType(StringUtils.isNotBlank(contentType) ? contentType : MediaType.APPLICATION_JSON_VALUE);
                response .setContentLength(newBytes.length);
            } else {
                for(ResponseHeaderPO po : responseHeaderList){
                    response.setHeader(po.getHeaderName(),po.getHeaderValue());
                }
            }
            // 移除当前线程的副本变虽值
            SaveResponseHeaderThread.getInstance().clear();
            StreamUtils.copy(newBytes,response.getOutputStream());
        } else {
            responseWrapper.copyBodyToResponse() ;
        }

    }

    private String byteToString(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
        return StringUtils.isBlank(characterEncoding) ? new String(contentAsByteArray,StandardCharsets.UTF_8)
                : new String(contentAsByteArray,characterEncoding);

    }

    private boolean isMockInterface(String requestURI) {
        return StringUtils.isNotBlank(requestURI) && requestURI.contains("mock");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
