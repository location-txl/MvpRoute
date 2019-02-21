/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.http;


import com.location.mvp.mvproutelibrary.base.BaseProgressObserver;
import com.location.mvp.mvproutelibrary.error.IResponseErrorMsg;

import retrofit2.Retrofit;
import com.location.mvp.mvproutelibrary.http.conver.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Proxy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * RetrofitClient
 * 网络基类
 * 在Application里面初始化
 */


public class RetrofitClient {
	private static final String TAG = "Retrofit";


	private static volatile RetrofitClient instance;
	private Retrofit client;


	private IResponseErrorMsg errorResponse;

	private INetWorkLoadingView loadingView;

	private IRefreshToken iRefreshToken;

	private ProxyHandler proxyHandler;

	private BaseProgressObserver progressObserver;

	public void setProgressObserver(BaseProgressObserver progressObserver) {
		this.progressObserver = progressObserver;
	}

	/**
	 * 初始化网络
	 *
	 * @param config RetrofitClient 配置文件
	 */
	public static void init(RetrofitConfig config) {
		initRetrofitClient(config);
	}

	/**
	 * 内部使用
	 * {@link com.location.mvp.mvproutelibrary.http.conver.GsonResponseBodyConverter}
	 */
	public IResponseErrorMsg getErrorResponse() {
		return errorResponse;
	}

	/**
	 * 内部使用
	 * {@link BaseObserver}
	 */
	public INetWorkLoadingView getLoadingView() {
		return loadingView;
	}

	/**
	 * init Retrofit
	 *
	 * @param config
	 */
	private RetrofitClient(RetrofitConfig config) {
		errorResponse = config.getiResponseErrorMsg();
		loadingView = config.getLoadingView();
		iRefreshToken = config.getiRefreshToken();
		if (config.getGsonClass() == null) {
			throw new NullPointerException("gsonClazz is null");
		}
		if (iRefreshToken != null) proxyHandler = new ProxyHandler(iRefreshToken);
		OkHttpClient.Builder builder = config.getBuilder() == null ? new OkHttpClient.Builder() : config.getBuilder();
//		builder.addInterceptor(progressInterceptor());
		client = new Retrofit.Builder()
				.client(builder.build())
				.baseUrl(config.getBaseUrl())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(config.getGsonClass()))
				.build();
	}

	public static RetrofitClient getInstance() {
		if (instance == null) {
			throw new NullPointerException("you need initialize RetrofitClient");
		}
		return instance;
	}


	private static RetrofitClient initRetrofitClient(RetrofitConfig retrofitConfig) {
		if (instance == null) {
			synchronized (RetrofitClient.class) {
				if (instance == null) {
					instance = new RetrofitClient(retrofitConfig);
				}
			}
		}
		return instance;
	}

	public <T> T createApi(Class<? extends T> clazz) {
		T t = client.create(clazz);
		if (iRefreshToken == null) {
			return t;
		}
		proxyHandler.setObject(t);
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxyHandler);
	}

	public <T> T createRefreshToken(Class<? extends T> clazz) {
		return client.create(clazz);
	}

	/**
	 * @deprecated  用于处理上传进度 和下载进度的拦截器 暂时废弃
	 * @return
	 */
	@Deprecated
	private Interceptor progressInterceptor() {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request();
				if (request.body() == null || progressObserver == null || progressObserver.getDisposable().isDisposed()) {
					return chain.proceed(request);
				}
				Request.Builder builder = request.newBuilder();
				Request build = builder.method(request.method(), new ProgressRequestBody(request.body(), progressObserver)).build();
				return chain.proceed(build);
			}
		};
	}
}
