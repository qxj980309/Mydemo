package com.example.demo.myself.common.result;

public enum Enum {
    SUCCESS(200,"成功");


    private int code;
    private String msg;
    Enum(int code ,String msg){
        this.code =code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
