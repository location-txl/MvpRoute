package com.location.mvp.mvp_route_demo;

import android.app.Application;

import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/7/2 0002 20:37
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new RetrofitClient.Builder("http://www.baidu.com/").build().createApiService();
        ToastUtils.init(this);

    }
}
