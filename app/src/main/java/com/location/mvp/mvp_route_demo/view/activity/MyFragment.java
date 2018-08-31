package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvproutelibrary.Base.BaseFragment;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/20 15:40
 * description：
 */

public class MyFragment extends BaseFragment {
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected void initView(View view) {

	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int getLayout() {
		return 0;
	}
}
