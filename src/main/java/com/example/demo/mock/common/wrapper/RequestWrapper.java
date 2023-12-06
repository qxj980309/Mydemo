package com.example.demo.mock.common.wrapper;

import com.example.demo.mock.common.filter.RequestFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/*
* ServletRequest 包装类
* */
public class RequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(RequestWrapper.class);

    private byte[] cacheBody;
    private String content;
    private Charset charset;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /*
    * 请求中的内容
    *
    * @return the content of request
    * */
    public String getContent() {
        if (null == this.content) {
            Charset encoding = getCharset();
            this.content = new String(getCacheBody() ,
                    null == encoding ? StandardCharsets.UTF_8 : encoding);
        }
        return this.content;
    }

    public Charset getCharset() {
        if (null != charset) {
            return charset;
        }
        charset = StringUtils.isNotBlank(this.getContentType()) ?
                MediaType.parseMediaType(this.getContentType()).getCharset()
                : StandardCharsets.UTF_8;
        return charset;
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getCacheBody());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
//                return false;
                return byteArrayInputStream.available() ==0;
            }

            @Override
            public boolean isReady() {
//                return false;
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
//                return 0;
                return byteArrayInputStream.read();
            }
        };
    }

    private byte[] getCacheBody() {
        // 首次从原始request中获取
        if (null == this.cacheBody) {
            // int len = super.getContentLength0); 不校验长度，有些传-1
            this.cacheBody = this.getByteArray(super.getRequest());
        }
        return this.cacheBody;
    }

    private byte[] getByteArray(ServletRequest request) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buff = new byte[1024];
            ServletInputStream inputStream = request.getInputStream();
            int read;
            while ((read = inputStream.read(buff))>0){
                outputStream.write(buff, 0, read);
            }
        }catch (Exception e){
            log.error("流读取失败",e);
        }
        return outputStream.toByteArray();
    }
}
