package com.location.mvp.mvproutelibrary.base;

import android.graphics.Color;

/**
 * create：2018/11/15 22:43
 *
 * @author location
 */

public class BaseManager {
	/**
	 * 是否为透明状态栏
	 */
	static boolean TRANSPARENR_STATUS = false;

	/**
	 * {@link #TRANSPARENR_STATUS} 设置为true才有效
	 * 默认状态栏颜色
	 */
	static int STATUS_COLOR = Color.TRANSPARENT;

	/**
	 * {@link #TRANSPARENR_STATUS} 设置为true才有效
	 * 是否是半透明
	 */
	static boolean STATUS_TRANSLUCENT = false;


	/**
	 * {@link #TRANSPARENR_STATUS} 设置为true才有效
	 * 设置透明状态栏后 内容是否显示在状态栏之下
	 */
	static boolean STATUS_PADDING_TOP = true;


}
