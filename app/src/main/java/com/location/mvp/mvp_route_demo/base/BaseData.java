package com.location.mvp.mvp_route_demo.base;

import com.location.mvp.mvproutelibrary.IBaseBean;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:31
 * description：
 */

public class BaseData<T> implements IBaseBean<T> {
	private int errorCode;
	private String errorMsg;
	private T data;

	@Override
	public T getData() {
		return data;
	}

	@Override
	public boolean isOk() {
		return errorCode==0;
	}

	@Override
	public String getErrorMsg() {
		return null;
	}

	@Override
	public int getStatusCode() {
		return errorCode;
	}
}
