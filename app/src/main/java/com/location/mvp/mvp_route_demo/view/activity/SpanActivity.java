package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.contract.SpanContract;
import com.location.mvp.mvp_route_demo.presenter.SpanPresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 18:33
 * description：
 */
@Layout(R.layout.activity_span)
public class SpanActivity extends BaseToActivity<SpanContract.Presenter> implements SeekBar.OnSeekBarChangeListener, SpanContract.View, View.OnClickListener {
	private SeekBar seekBar;

	private TextView maxText;

	private TextView spanTextBack;

	private TextView spanColorText;

	private TextView spanOne, spanTwo, SpanThree;

	private TextView spanClickText;

	@Override
	public void onshowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "SpanUtils丰富的文本效果";
	}


	@Override
	protected void loadData() {
		presenter.setBackTextColor("设置某段文字背景色");
		presenter.setForgetTextColor(getString(R.string.span_forget_text));
		spanOne.setText(SpanUtils.Builder(spanOne.getText().toString()).setBorder(0, spanOne.getText().toString().length()).setUnderline().build());
		spanTwo.setText(SpanUtils.Builder(spanTwo.getText().toString()).setBorder(0, spanTwo.getText().toString().length()).setStrik().build());
		SpanThree.setText(SpanUtils.Builder(SpanThree.getText().toString()).setBorder(0, SpanThree.getText().toString().length()).setBold().build());
		presenter.setSpanClick("西游记和水浒传");

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState ){
		super.initView(savedInstanceState);
		seekBar = findViewById(R.id.span_seekbar);
		seekBar.setMax(200);
		seekBar.setOnSeekBarChangeListener(this);
		maxText = findViewById(R.id.span_max_size);
		seekBar.setProgress(100);
		spanTextBack = findViewById(R.id.span_text_back);
		findViewById(R.id.span_color_click).setOnClickListener(this);
		spanColorText = findViewById(R.id.span_color_text);
		spanOne = findViewById(R.id.span_style_one);
		spanTwo = findViewById(R.id.span_style_two);
		SpanThree = findViewById(R.id.span_style_three);
		spanClickText = findViewById(R.id.span_text_clcik);
		//设置文字点击事件必须调用这个方法
		spanClickText.setMovementMethod(LinkMovementMethod.getInstance());

	}

	@NonNull
	@Override
	protected SpanContract.Presenter createPresenter() {
		return new SpanPresenter();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		presenter.setMaxtext("滑动以调整大小", (float) (progress / 100.0));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void showMaxText(CharSequence charSequence) {
		maxText.setText(charSequence);
	}

	@Override
	public void showBackText(CharSequence charSequence) {
		spanTextBack.setText(charSequence);
	}

	@Override
	public void showColorText(CharSequence charSequence) {
		spanColorText.setText(charSequence);
	}

	@Override
	public void showClickText(CharSequence charSequence) {
		spanClickText.setText(charSequence);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.span_color_click:
				presenter.setBackTextColor("设置某段文字背景色");
				System.gc();
				break;
			default:
		}
	}
}
