package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/25 11:35
 * description：
 */

public class MainActivity extends BaseActivity {

	private TestView testView;

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

}
