package com.location.mvp.mvproutelibrary.Base;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 21:27
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseBean<T> {
    /**
     * 由其子类重新实现
     * @return
     */

    public boolean showError() {
        return false;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
