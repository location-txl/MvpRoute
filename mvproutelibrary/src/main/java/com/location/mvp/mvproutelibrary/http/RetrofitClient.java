package com.location.mvp.mvproutelibrary.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static final String TAG = "Mvp_moute";
    private static RetrofitClient instance;
    private Retrofit client;

    private RetrofitClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.e(TAG, "url===>" + request.url().toString());
                Log.e(TAG, "method===>" + request.method());
                Log.e(TAG, "header===>" + request.headers().toString());
//                Log.e(TAG, "params====>" + request.toString());
                Log.e(TAG, "response--------------------------------");
                Response proceed = chain.proceed(request);

                Log.e(TAG, "time" + proceed.sentRequestAtMillis());

                Log.e(TAG, "message" + proceed.message());


                Log.e(TAG, "headers===>" + proceed.headers().toString());
//                Log.e(TAG, "body===>" + proceed.body().string());
//                proceed.close();
                return proceed;
            }
        });


        client = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("http://www.wanandroid.com/tools/mockapi/428/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
