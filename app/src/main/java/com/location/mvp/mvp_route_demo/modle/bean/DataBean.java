package com.location.mvp.mvp_route_demo.modle.bean;

import android.support.annotation.StringRes;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/30 9:53
 * description：
 */

public class DataBean  {
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_IMAGE = 1;
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
}
