package com.location.mvp.mvp_route_demo.base;


import android.app.ProgressDialog;
import android.content.Context;

import com.location.mvp.mvproutelibrary.http.INetWorkLoadingView;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/9 20:37
 * description：
 */

public class LoadingView implements INetWorkLoadingView {
private ProgressDialog dialog;
	@Override
	public void createLoadingView(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setTitle("加载中");
	}

	@Override
	public void showLoading() {
		dialog.show();
	}
	@Override
	public void dismissLoading() {
		dialog.dismiss();
	}
}
