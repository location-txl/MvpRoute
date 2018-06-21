package com.location.mvp.mvp_route_demo.demo;

import com.location.mvp.mvproutelibrary.adapter.MulitGroupListener;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 17:25
 * description：
 * group分组实现  相同的id会认为在一个组内
 */

public class GroupBean implements MulitGroupListener {
	private int groupId;
	private String messgae;

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getMessgae() {
		return messgae;
	}

	public GroupBean(String messgae) {
		this.messgae = messgae;
	}

	@Override
	public int bindAdapterGroupId() {
		return groupId;
	}
}
