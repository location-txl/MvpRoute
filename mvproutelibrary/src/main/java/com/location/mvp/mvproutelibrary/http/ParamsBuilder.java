package com.location.mvp.mvproutelibrary.http;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.location.mvp.mvproutelibrary.service.ApiService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/6/2 0002 16:24
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class ParamsBuilder {
    public static final int METHOD_POST = 1000000000;
    public static final int METHOD_GET = 2000000000;


    private ApiService apiService;

    private int method;

    private String pathUrl;

    private ArrayMap<String, String> headers;

    private ArrayMap<String, String> params;

    public ParamsBuilder(ApiService apiService, int method) {
        this.apiService = apiService;
        this.method = method;
    }

    public ParamsBuilder url(String url) {
        this.pathUrl = url;
        return this;
    }

    @SuppressLint("NewApi")
    public ParamsBuilder addHeader(String key, String value) {
        if (headers == null) {
            headers = new ArrayMap<>();
        }
        headers.put(key, value);
        return this;
    }

    @SuppressLint("NewApi")
    public ParamsBuilder headers(Map<String, String> headers) {
        if (headers == null) {
            this.headers = new ArrayMap<>();
        }
        this.headers.putAll(headers);
        return this;
    }

    @SuppressLint("NewApi")
    public ParamsBuilder setBaseUrl(String baseUrl) {
        if (headers == null) {
            headers = new ArrayMap<>();
        }
        headers.put(RetrofitClient.HEADER_URL, baseUrl);
        return this;
    }

    @SuppressLint("NewApi")
    public ParamsBuilder addParams(String key, String value) {
        if (params == null) params = new ArrayMap<>();
        params.put(key, value);
        return this;
    }

    @SuppressLint("NewApi")
    public ParamsBuilder params(Map<String, String> params) {
        if (params == null) {
            this.params = new ArrayMap<>();
        }
        this.params.putAll(params);
        return this;
    }

    @SuppressLint("NewApi")
    public Observable<ResponseBody> create() {
        if (TextUtils.isEmpty(pathUrl)) {
            throw new NullPointerException("pathurl is null");
        }
        if (params == null) params = new ArrayMap<>();
        if (headers == null) headers = new ArrayMap<>();
        if (!headers.containsKey(RetrofitClient.HEADER_URL)) {
            headers.put(RetrofitClient.HEADER_URL, RetrofitClient.DEFAULT_URL);
        }
        switch (method) {
            case METHOD_GET:
                return apiService.get(pathUrl, params, headers);
            case METHOD_POST:
                return apiService.post(pathUrl, params, headers);
            default:
                return apiService.get(pathUrl, params, headers);
        }
    }
}
