package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.Base.BaseBean;
import com.location.mvp.mvproutelibrary.api.IAPiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 22:09
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit client;
//nnnnnnnn
    private RetrofitClient() {

        client = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/tools/mockapi/428/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public <T> io.reactivex.Observable<BaseBean<T>> get(String url) {
        IAPiService iaPiService = client.create(IAPiService.class);
        return iaPiService.<T>get(url);
    }

    public static RetrofitClient getRetrofitClient() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public <T> T createApi(Class<? extends T> clazz) {
        return client.create(clazz);
    }
}
