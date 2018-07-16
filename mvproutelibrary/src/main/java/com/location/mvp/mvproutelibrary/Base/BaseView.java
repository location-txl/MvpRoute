package com.location.mvp.mvproutelibrary.Base;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 19:18
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface BaseView {
    void onshowError(ExceptionHandle.ResponeThrowable baseThrowable);
}
