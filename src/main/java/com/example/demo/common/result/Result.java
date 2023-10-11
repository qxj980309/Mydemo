package com.example.demo.common.result;

import java.io.Serializable;

public class Result<T> implements Serializable {


    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object  data;

    private static final Integer success=200;
    private static final Integer error=500;
//    private static final String successMsg="成功";
//    private static final String errorMsg="失败";

    public static Result error( ) {
        return new Result();
    }
    public static Result error( String msg) {
        return new Result(msg);
    }
    public static Result error(Integer code){
        return new Result(code);
    }

    public static Result error(Integer code,String msg){
        return new Result(code, msg,null);
    }
//    public static Result error(int code, String msg) {
//        Result r = new Result();
//        r.setCode(code);
//        r.setMsg(msg);
//        return r;
//    }


    public static Result error(Integer code, String msg, Object data) {
//        return new Results(Results.error, msg, data);
        return new Result(code, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok() {
        return new Result(null);
    }
    public static Result ok(Integer code) {
        return new Result(code);
//        return new Result(Enum.SUCCESS.getCode());
    }

    public static Result ok(Integer code,String msg) {
//        return new Result(Enum.SUCCESS.getCode(),Enum.SUCCESS.getMsg(),null);
        return new Result(code, msg, null);
    }



    public static Result ok(Integer code,String msg,Object data) {
        return new Result(code, msg, data);
    }

    public Result() {

    }

    public static Result build(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public Result(Integer code, String msg, Object  data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.code = success;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object  getData() {
        return data;
    }

    public void setData(Object  data) {
        this.data = data;
    }
}

//
//    private Integer code;
//    private String msg;
//    private T data;
//
//    public Result() {
//    }
//
//    public Result(Integer code, String msg, T data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }
//
//    public Result(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public Result(int code, String msg, T data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public Result setCode(Integer code) {
//        this.code = code;
//        return this;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public Result setMsg(String msg) {
//        this.msg = msg;
//        return this;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public Result setData(T data) {
//        this.data = data;
//        return this;
//    }
