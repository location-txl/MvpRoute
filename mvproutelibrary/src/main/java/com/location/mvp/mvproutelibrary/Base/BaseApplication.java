package com.location.mvp.mvproutelibrary.Base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.Log;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/22 0022 22:32
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseApplication extends Application {
    public static Context context;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }
}
