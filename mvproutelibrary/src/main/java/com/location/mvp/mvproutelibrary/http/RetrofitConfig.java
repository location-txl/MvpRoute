package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.error.IResponseErrorMsg;

import okhttp3.OkHttpClient;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 13:14
 * description：
 */

public class RetrofitConfig {
	private String baseUrl;

	private OkHttpClient.Builder builder;

	private IResponseErrorMsg iResponseErrorMsg;

private IRefreshToken iRefreshToken;


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
}
