package com.location.mvp.mvproutelibrary.Base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.location.mvp.mvproutelibrary.utils.LogUtils;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/22 0022 22:32
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class BaseApplication extends Application {
public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG","BaseApplication");
        this.context = this;
        Log.println(Log.DEBUG,"哈哈哈","哈哈哈");
    }
}
