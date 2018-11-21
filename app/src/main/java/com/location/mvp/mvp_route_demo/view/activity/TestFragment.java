package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvproutelibrary.base.BaseFragment;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/9 21:23
 * description：
 */

public class TestFragment extends BaseFragment {
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
