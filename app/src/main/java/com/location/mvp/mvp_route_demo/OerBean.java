package com.location.mvp.mvp_route_demo;

import com.location.mvp.mvproutelibrary.adapter.MulitTypeListener;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/28 21:58
 * description：
 */

public class OerBean  implements MulitTypeListener{
	private String name;
	private int id;
	private int type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int getItemType() {
		return type;
	}

	public OerBean(String name, int id, int type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}

	public OerBean(String name, int type) {
		this.name = name;
		this.type = type;
	}
}
