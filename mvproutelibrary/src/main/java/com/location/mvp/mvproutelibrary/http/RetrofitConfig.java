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

import com.location.mvp.mvproutelibrary.error.IResponseErrorMsg;

import okhttp3.OkHttpClient;


/**
 *   retrofit 配置类
 *   <p>
 *       在这里可以设置retrofit的配置信息
 *   </p>
 */

public class RetrofitConfig {
	/**
	 * retrofit base url
	 */
	private String baseUrl;


	/**
	 * OkHttpClient.Builder
	 * 在这里你可以设置拦截器   超时时间 等等
	 */
	private OkHttpClient.Builder builder;

	/**
	 * 设置异常解析类
	 */
	private IResponseErrorMsg iResponseErrorMsg;

	/**
	 * 设置刷新token类
	 */
	private IRefreshToken iRefreshToken;

	/**
	 * 设置gson解析父类
	 */
	private Class<? extends IBaseBean> clazz;

	/**
	 * 设置 loadingView
	 */
	private INetWorkLoadingView loadingView;

	public IRefreshToken getiRefreshToken() {
		return iRefreshToken;
	}

	public void setiRefreshToken(IRefreshToken iRefreshToken) {
		this.iRefreshToken = iRefreshToken;
	}

	public void setErrorResponse(IResponseErrorMsg errorResponse) {
		this.iResponseErrorMsg = errorResponse;
	}

	public RetrofitConfig(String baseUrl) {
		this.baseUrl = baseUrl;
	}


	public void setGsonClass(Class<? extends IBaseBean> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends IBaseBean> getGsonClass() {
		return clazz;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public OkHttpClient.Builder getBuilder() {
		return builder;
	}

	public void setBuilder(OkHttpClient.Builder builder) {
		this.builder = builder;
	}

	public IResponseErrorMsg getiResponseErrorMsg() {
		return iResponseErrorMsg;
	}

	public void setLodingView(INetWorkLoadingView lodingView) {
		this.loadingView = lodingView;
	}

	public INetWorkLoadingView getLoadingView() {
		return loadingView;
	}
}
