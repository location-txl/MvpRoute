package com.location.mvp.mvproutelibrary.http;

import android.content.Context;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/9 20:16
 * description：
 */

public interface INetWorkLoadingView {


	void createLoadingView(Context context);

	void showLoading();


	void dismissLoading();
}
