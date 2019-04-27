package com.location.mvp.mvp_route_demo;

import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * @author tianxiaolong
 * time：19-4-27 下午5:35
 * description：
 */
@Layout(R.layout.activity_slide)
public class SlideLayoutTestActivity extends BaseToActivity {
	@Override
	protected String getTooBarTitle() {
		return "测试SlideLayout";
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}
}
