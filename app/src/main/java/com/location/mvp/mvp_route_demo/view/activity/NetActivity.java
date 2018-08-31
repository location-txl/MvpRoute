package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.presenter.NetPresenter;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:39
 * description：
 */

public class NetActivity extends BaseToActivity<NetContract.Presenter> implements NetContract.View {
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {
		ToastUtils.showShort(baseThrowable.msg);
		LogUtils.d("失败===>"+baseThrowable.msg);
	}

	@Override
	protected String getTooBarTitle() {
		return "网络请求";
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_net;
	}

	@Override
	protected void loadData() {
		findViewById(R.id.net_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.loginWanAndroid("tianxiaolong", "tianxiaolong123");
			}
		});
	}

	@NonNull
	@Override
	protected NetContract.Presenter createPresenter() {
		return new NetPresenter();
	}

	@Override
	public void showMessage(String msg) {
		ToastUtils.showShort(msg);
	}
}
