package com.location.mvp.mvproutelibrary.Base;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 21:27
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseBean<T> {
    private String message;
    private int code;
    private T data;


    public boolean isOk() {
       if(code==200){
           return true;
       }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
