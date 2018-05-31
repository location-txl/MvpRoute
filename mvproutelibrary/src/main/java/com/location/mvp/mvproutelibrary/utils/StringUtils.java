package com.location.mvp.mvproutelibrary.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/31 17:10
 * description：文字工具类
 */

public class StringUtils {


	public static CharSequence stringForColor(String message, int startIndex, int endIndex, int color) {
		SpannableString spannableString = new SpannableString(message);
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static CharSequence stringForBackGround(String message, int startIndex, int endIndex, int color) {

		SpannableString spannableString = new SpannableString(message);
		BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
		spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}


	public static CharSequence stringSize(String message, int startIndex, int endIndex, float scale) {
		SpannableString spannableString = new SpannableString(message);
		RelativeSizeSpan sizeSpan = new RelativeSizeSpan(scale);
		spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static CharSequence stringStrik(String message, int startIndex, int endIndex) {
		SpannableString spannableString = new SpannableString(message);
		StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
		spannableString.setSpan(strikethroughSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static CharSequence stringUnderline(String message, int startIndex, int endIndex) {
		SpannableString spannableString = new SpannableString(message);
		UnderlineSpan underlineSpan = new UnderlineSpan();
		spannableString.setSpan(underlineSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static CharSequence stringBold(String message, int startIndex, int endIndex) {
		SpannableString spannableString = new SpannableString(message);
		StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
		spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static CharSequence stringItailic(String message, int startIndex, int endIndex) {
		SpannableString spannableString = new SpannableString(message);
		StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
		spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}


}
