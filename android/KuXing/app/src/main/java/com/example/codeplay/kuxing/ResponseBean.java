package com.example.codeplay.kuxing;

public class ResponseBean {
    public int code;
    public String msg;
    public String data;
    public boolean temp;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isTemp() {
        return temp;
    }
}
