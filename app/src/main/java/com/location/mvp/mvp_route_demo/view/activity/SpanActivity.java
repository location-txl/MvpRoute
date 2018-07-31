package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.contract.SpanContract;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 18:33
 * description：
 */

public class SpanActivity extends BaseToActivity<SpanContract.Presenter> implements SeekBar.OnSeekBarChangeListener ,{
	private SeekBar seekBar;



	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "SpanUtils丰富的文本效果";
	}

	@Override
	protected int getLayout() {
		return 0;
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected void initView() {
		super.initView();
		seekBar = findViewById(R.id.span_seekbar);
		seekBar.setMax(200);
		seekBar.setOnSeekBarChangeListener(this);


	}

	@NonNull
	@Override
	protected SpanContract.Presenter createPresenter() {
		return null;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
}
