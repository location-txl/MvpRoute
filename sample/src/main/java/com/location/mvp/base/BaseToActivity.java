package com.location.mvp.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.location.mvp.R;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:10
 * description：
 */

public abstract class BaseToActivity<P extends BasePresenter> extends BaseActivity<P> {
	protected abstract String getTooBarTitle();

	protected TextView titleView;

	@CallSuper
	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		titleView = findViewById(R.id.toobar_title);
		if (titleView != null && !TextUtils.isEmpty(getTooBarTitle())) {
			titleView.setText(getTooBarTitle());
			findViewById(R.id.bar_back).setOnClickListener(v -> onBackPressed());
		}
	}
}
