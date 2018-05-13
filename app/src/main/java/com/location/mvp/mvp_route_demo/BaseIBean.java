package com.location.mvp.mvp_route_demo;

import com.location.mvp.mvproutelibrary.Base.BaseBean;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 22:16
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseIBean<T> extends BaseBean<T> {
    private int code;
    private String message;
    private T result;

    @Override
    public T getData() {
        return result;
    }

    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public String getMessgae() {
        return message;
    }

    public void setMessgae(String messgae) {
        this.message = messgae;
    }
}
