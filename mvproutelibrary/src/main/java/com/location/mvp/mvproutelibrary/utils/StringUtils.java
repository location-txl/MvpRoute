package com.location.mvp.mvproutelibrary.utils;

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
    private SpannableString span;

    public StringUtils(String message) {
        span = new SpannableString(message);
    }


    /**
     * 设置某段文字的颜色
     *
     * @param startIndex 从哪开始（包括）
     * @param endIndex   从哪结束 （不包括）
     * @param color      设置的颜色
     * @return
     */
    public StringUtils stringForColor(int startIndex, int endIndex, int color) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        span.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置某段文字的背景色
     *
     * @param startIndex
     * @param endIndex
     * @param color
     * @return
     */
    public StringUtils stringForBackGround(int startIndex, int endIndex, int
            color) {
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        span.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }


    /**
     * 设置某段文字的大小
     *
     * @param startIndex
     * @param endIndex
     * @param scale      大小比例 基于原文字大小  1为原大小  1.2为原文字大小的1.2倍
     * @return
     */
    public StringUtils stringSize(int startIndex, int endIndex, float scale) {
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(scale);
        span.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }


    /**
     * 设置删除线
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    public StringUtils stringStrik(int startIndex, int endIndex) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        span.setSpan(strikethroughSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /***
     * 设置下划线
     * @param startIndex
     * @param endIndex
     * @return
     */
    public StringUtils stringUnderline(int startIndex, int endIndex) {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        span.setSpan(underlineSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置粗体
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    public StringUtils stringBold(int startIndex, int endIndex) {
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        span.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置斜体
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    public StringUtils stringItailic(int startIndex, int endIndex) {
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        span.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }


    public CharSequence build() {
        return span;
    }

}
