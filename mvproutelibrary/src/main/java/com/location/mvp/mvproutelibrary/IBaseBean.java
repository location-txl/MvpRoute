package com.location.mvp.mvproutelibrary;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:28
 * description：
 */

public interface IBaseBean<T> {

	T getData();

	boolean isOk();

	String getErrorMsg();

	int   getStatusCode();
}
