package com.location.mvp.mvp_route_demo.base;

import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
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
	protected void initView() {
		titleView = findViewById(R.id.toobar_title);
		if (titleView != null && !TextUtils.isEmpty(getTooBarTitle())) {
			titleView.setText(getTooBarTitle());
			findViewById(R.id.bar_back).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}

	}
}
