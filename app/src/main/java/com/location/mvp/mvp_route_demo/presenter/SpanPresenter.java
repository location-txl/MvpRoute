package com.location.mvp.mvp_route_demo.presenter;

import android.graphics.Color;
import android.icu.text.UnicodeSet;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.location.mvp.mvp_route_demo.contract.SpanContract;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.Random;

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
				.setBorder(3, 5)
				.setTextSize(textsize)
				.build();
		view.showMaxText(build);
	}

	@Override
	public int randomColor() {
		Random random = new Random();
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return Color.rgb(r,g,b);
	}

	@Override
	public void setBackTextColor(String message) {
		CharSequence build = SpanUtils.Builder(message)
				.setBorder(2, 6)
				.setForBackGround(randomColor())
				.build();
		view.showBackText(build);
	}

	@Override
	public void setForgetTextColor(String message) {
		CharSequence build = SpanUtils.Builder(message)
				.setBorder(2, 8)
				.setTextColor(Color.RED)
				.build();
		view.showColorText(build);
	}

	@Override
	public void setSpanClick(String message) {
		CharSequence build = SpanUtils.Builder(message)
				.setBorder(0, 3)
				.setClick(new ClickableSpan() {
					@Override
					public void updateDrawState(TextPaint ds) {
						//这个方法可以取消下划线
						ds.setUnderlineText(false);
					}
					@Override
					public void onClick(View widget) {
						ToastUtils.showShort("西游记");
					}
				}).setBorder(4, message.length())
				.setClick(new ClickableSpan() {
					@Override
					public void onClick(View widget) {
						ToastUtils.showShort("水浒传");
					}
				}).build();
		view.showClickText(build);
	}

}
