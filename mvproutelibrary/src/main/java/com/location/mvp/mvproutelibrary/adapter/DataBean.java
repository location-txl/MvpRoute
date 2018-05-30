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
	private int type;
	private @LayoutRes int layout;

	public DataBean(T response, int type, @LayoutRes int layout) {
		this.response = response;
		this.type = type;
		this.layout = layout;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
