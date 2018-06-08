package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.LayoutRes;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/30 18:07
 * description：
 */

public class DataBean<T> {
	private T response;
	private @LayoutRes int layout;

	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public DataBean(T response,  @LayoutRes int layout) {
		this.response = response;
		this.layout = layout;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}
