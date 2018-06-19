package com.location.mvp.mvp_route_demo.modle.bean;

import android.support.annotation.StringRes;

import com.location.mvp.mvproutelibrary.adapter.MulitTypeListener;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/30 9:53
 * description：
 */

public class DataBean implements MulitTypeListener {
	public static final int TYPE_TEXT = 12;
	public static final int TYPE_IMAGE = 13;
	private int type;
	private String message;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DataBean(int type, String message) {
		this.type = type;
		this.message = message;
	}
	public DataBean() {
	}

	@Override
	public int getItemType() {
		return type;
	}
}
