package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:07
 * description：
 */

public class BackActivity extends BaseActivity implements View.OnClickListener {
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_back;
	}

	@Override
	protected void initView() {
		findViewById(R.id.back_result).setOnClickListener(this);
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
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("resultSuccful", "回传成功");
		setResult(RESULT_OK, intent);
		finish();

	}
}
