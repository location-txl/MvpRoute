package com.location.mvp.mvproutelibrary.utils;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

/**
 * 项目:Mvp_Route_Demo
 * author：location
 * description：文字工具类
 */

public class SpanUtils {
	private SpannableString span;
	private int startIndex;

	private int endIndex;

	private SpanUtils(String message) {
		span = new SpannableString(message);
	}

	public static SpanUtils Builder(String message) {
		return new SpanUtils(message);
	}


	/**
	 * 设置边界方法
	 *
	 * @param startIndex 从哪开始（包括）
	 * @param endIndex   从哪结束 （不包括）
	 * @return
	 */
	public SpanUtils setBorder(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		return this;
	}

	/**
	 * 设置某段文字的颜色
	 *
	 * @param color 设置的颜色
	 * @return
	 */
	public SpanUtils setTextColor(int color) {
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		span.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/**
	 * 设置某段文字的背景色
	 *
	 * @param color
	 * @return
	 */
	public SpanUtils setForBackGround(int color) {
		BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
		span.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}


	/**
	 * 设置某段文字的大小
	 *
	 * @param scale 大小比例 基于原文字大小  1为原大小  1.2为原文字大小的1.2倍
	 * @return
	 */
	public SpanUtils setTextSize(float scale) {
		RelativeSizeSpan sizeSpan = new RelativeSizeSpan(scale);
		span.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}


	/**
	 * 设置删除线
	 *
	 * @return
	 */
	public SpanUtils setStrik() {
		StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
		span.setSpan(strikethroughSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/***
	 * 设置下划线
	 * @return
	 */
	public SpanUtils setUnderline() {
		UnderlineSpan underlineSpan = new UnderlineSpan();
		span.setSpan(underlineSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/**
	 * 设置粗体
	 *
	 * @return
	 */
	public SpanUtils setBold() {
		StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
		span.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/**
	 * 设置斜体
	 *
	 * @return
	 */
	public SpanUtils setItailic() {
		StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
		span.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}


	/**
	 * 设置文字的点击效果
	 * 其中  textView 必须设置 setMovementMethod方法  否则没有点击响应
	 * setHighlightColor  是点击时的背景色 可选
	 *
	 * @param click 点击事件回调
	 * @return
	 */
	public SpanUtils setClick(ClickableSpan click) {
		span.setSpan(click, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/**
	 * 为文字设置上标
	 * @return
	 */
	public SpanUtils setSuperscript(){
		SuperscriptSpan superscriptSpan = new SuperscriptSpan();
		span.setSpan(superscriptSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	/**
	 * 为文字设置下标
	 * @return
	 */
	public SpanUtils setSubscriptSpan(){
		SubscriptSpan subscriptSpan = new SubscriptSpan();
		span.setSpan(subscriptSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return this;
	}

	public CharSequence build() {
		return span;
	}

}
