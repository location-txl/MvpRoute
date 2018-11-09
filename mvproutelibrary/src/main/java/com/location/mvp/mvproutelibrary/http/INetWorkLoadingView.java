package com.location.mvp.mvproutelibrary.http;

import android.content.Context;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/9 20:16
 * description：
 */

public interface INetWorkLoadingView {


	/**
	 * 初始化弹窗
	 *
	 * @param context
	 */
	void createLoadingView(Context context);

	/**
	 * 显示弹窗
	 */
	void showLoading();


	/**
	 * 关闭弹窗
	 */
	void dismissLoading();
}
