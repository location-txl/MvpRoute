package com.location.mvp.mvp_route_demo.modle.bean;

import com.location.mvp.mvproutelibrary.adapter.MulitGroupListener;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 14:02
 * description：
 */

public class GroupData implements MulitGroupListener {
	private int groupId;

	public GroupData(int groupId, String message) {
		this.groupId = groupId;
		this.message = message;
	}

	private String message;

	@Override
	public int bindAdapterGroupId() {
		return groupId;
	}
}
