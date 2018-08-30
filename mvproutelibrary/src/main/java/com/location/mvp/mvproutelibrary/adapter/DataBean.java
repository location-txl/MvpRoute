package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

/**
 * 项目:Mvp_Route_Demo
 * author：location
 * time：2018/5/30 18:07
 * description：
 */

public class DataBean {
	private @Nullable Object response;
	private @LayoutRes int layout;



	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public DataBean(Object response,  @LayoutRes int layout) {
		this.response = response;
		this.layout = layout;
	}

	public DataBean(int layout) {
		this.layout = layout;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
