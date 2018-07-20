package com.location.mvp.mvproutelibrary.listener;

import android.view.View;

import java.util.Calendar;

/**
 * @author location
 * 防止多次点击
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

	public static final int MIN_CLICK_DELAY_TIME = 1000;
	private long lastClickTime = 0;

	@Override
	public void onClick(View v) {
		long currentTime = Calendar.getInstance().getTimeInMillis();
		if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
			lastClickTime = currentTime;
			onNoDoubleClick(v);
		}
	}

	public abstract void onNoDoubleClick(View v);

}