package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:07
 * description：
 */
@Layout(R.layout.activity_back)
public class BackActivity extends BaseActivity implements View.OnClickListener {
	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}


	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
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
