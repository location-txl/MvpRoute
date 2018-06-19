package com.location.mvp.mvp_route_demo.demo;

import com.location.mvp.mvproutelibrary.adapter.MulitTypeListener;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 16:15
 * description：
 * 多类型布局实体类实现  MulitTypeListener 接口
 * 返回对应的类型
 */

public class TypeBean implements MulitTypeListener{
	public static final int TYPE_TEXT = 0x001;
	public static final int TYPE_IMG = 0x002;
	private int type;
	private String message;

	public TypeBean(int type, String message) {
		this.type = type;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getItemType() {
		return type;
	}
}
