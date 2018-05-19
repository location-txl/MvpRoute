package com.location.mvp.mvproutelibrary.Base;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/19 0019 21:50
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseThrowable extends Throwable {
    private String message;
    private String code;

    public BaseThrowable(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
