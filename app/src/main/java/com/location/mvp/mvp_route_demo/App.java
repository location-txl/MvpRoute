package com.location.mvp.mvp_route_demo;

import android.widget.Toast;

import com.location.mvp.mvproutelibrary.Base.BaseApplication;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.lang.reflect.Method;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 20:43
 * description：
 */

public class App extends BaseApplication {

    public static RetrofitClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtils.init(this);
        ToastUtils.init(this);
        client = new RetrofitClient.Builder("http://www.wanandroid.com/tools/mockapi/428/")
                .build();
        client.createApiService();
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(this, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {
            }
        }
    }


}
