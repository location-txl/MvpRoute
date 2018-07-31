package com.location.mvp.mvp_route_demo.presenter;

import android.icu.text.UnicodeSet;

import com.location.mvp.mvp_route_demo.contract.SpanContract;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 18:37
 * description：
 */

public class SpanPresenter  extends SpanContract.Presenter{

	@Override
	public void setMaxtext(String message,float textsize) {
		CharSequence build = SpanUtils.Builder(message)
				.setBorder(1, 3)
				.setTextSize(textsize)
				.build();
		view.showMaxText(build);

	}
}
