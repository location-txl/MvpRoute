package com.location.mvp.mvproutelibrary.http;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Log;

import com.location.mvp.mvproutelibrary.service.ApiService;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
    private static final String HEADER_URL = "header_url";
    private static final String TAG = "Mvp_moute";
    private static RetrofitClient instance;
    private Retrofit client;

    private ApiService apiService;

    private ArrayMap<String, String> urlMap;


    @SuppressLint("NewApi")
    private RetrofitClient(@NonNull String baseUrl, @NonNull OkHttpClient okHttpClient) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.e(TAG, "url===>" + request.url().toString());
                Log.e(TAG, "method===>" + request.method());
                Log.e(TAG, "header===>" + request.headers().toString());
                Log.e(TAG, "response--------------------------------");
                Response proceed = chain.proceed(request);
                okhttp3.MediaType mediaType = proceed.body().contentType();
                String content = proceed.body().string();
                Log.e(TAG, "time" + proceed.sentRequestAtMillis());

                Log.e(TAG, "message" + proceed.message());
                Log.e(TAG, "headers===>" + proceed.headers().toString());
                Log.e(TAG, "body===>" + content);
                return proceed.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }
        });
        urlMap = new ArrayMap<>();
        client = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("http://www.wanandroid.com/tools/mockapi/428/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public RetrofitClient setBaseUrl(String url) {
        urlMap.put(HEADER_URL, url);
        return this;
    }


    /**
     * 设置内置Apiservice方法  只需要在app中国调用一次
     */
    public void createApiService() {
        apiService = client.create(ApiService.class);
    }


    public @NonNull
    Observable<ResponseBody> get() {
        return null;
    }

    private static RetrofitClient getRetrofitClient(@NonNull String baseurl, @NonNull OkHttpClient
            okHttpClient) {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient(baseurl, okHttpClient);
                }
            }
        }
        return instance;
    }

    public <T> T createApi(Class<? extends T> clazz) {
        return client.create(clazz);
    }


    public static class RetrofitCLientBuilder {
        private OkHttpClient.Builder builder;
        private String baseUrl;

        public RetrofitCLientBuilder(String baseurl) {
            this.baseUrl = baseurl;
            builder = new OkHttpClient.Builder();
        }

        public RetrofitClient build() {
            return RetrofitClient.getRetrofitClient(baseUrl, builder.build());
        }
    }
}
