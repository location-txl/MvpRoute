package com.location.mvp.mvp_route_demo.bean;

import com.location.mvp.mvproutelibrary.adapter.MulitTypeListener;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/16 18:52
 * description：
 */

public class ItemTypeResponse implements MulitTypeListener {
	private int type;
	private String content;

	public ItemTypeResponse(int type, String content) {
		this.type = type;
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int getItemType() {
		return type;
	}
}
