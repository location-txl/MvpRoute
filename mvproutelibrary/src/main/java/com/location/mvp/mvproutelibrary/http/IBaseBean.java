package com.location.mvp.mvproutelibrary.http;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:28
 * description：
 */

public interface IBaseBean<T> {

	/**
	 * 返回你想要的数据源
	 * @return
	 */
	T getData();

	/**
	 * 是否请求成功
	 * @return
	 */
	boolean isOk();

	/**
	 * 错误原因
	 * @return
	 */
	String getErrorMsg();

	/**
	 * 返回状态码
	 * @return
	 */
	int   getStatusCode();
}
