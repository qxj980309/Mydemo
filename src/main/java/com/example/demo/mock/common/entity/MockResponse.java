package com.example.demo.mock.common.entity;

public class MockResponse {
    /*
    * http 状态码
    * */
    private Integer status;
    private String body;
    private String errMsg;

    public MockResponse(Integer status,String body){
        this.status = status;
        this.body = body;
    }

    public MockResponse(String body){
        this.body = body;
    }

    public MockResponse(String body,String errMsg){
        this.errMsg = errMsg;
        this.body = body;
    }

    public MockResponse(Integer status,String body,String errMsg){
        this.status = status;
        this.body = body;
        this.errMsg = errMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
