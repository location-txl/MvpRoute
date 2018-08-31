package com.location.mvp.mvp_route_demo.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.location.mvp.mvp_route_demo.contract.ResultContract;
import com.location.mvp.mvp_route_demo.view.activity.BackActivity;
import com.location.mvp.mvp_route_demo.view.activity.ResultActivity;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:05
 * description：
 */

public class ResultPresenter extends ResultContract.Presenter {
	@Override
	public void initSince(String since) {
		CharSequence build = SpanUtils.Builder(since)
				.setBorder(12, 29)
				.setTextColor(Color.parseColor("#a800ff"))
				.build();
		view.showSince(build);


	}

	@Override
	public void startActivity(ResultActivity context, int requestcode) {
		context.startActivityForResult(new Intent(context, BackActivity.class), requestcode);
	}
}
