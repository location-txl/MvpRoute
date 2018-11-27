/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.base;


import android.content.Context;
import android.support.annotation.NonNull;

import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;


/**
 * @author location
 *         MvpRoute管理类
 *         两个初始化方法 如果不传options 会使用默认值
 *         {@link #init(Context)}
 *         {@link #init(Context, RouteOptions)}
 * @See #STATUS_PADDING_TOP
 * @see #TRANSPARENR_STATUS
 * @see #STATUS_COLOR
 * @see #filterClickTime
 * @see #context
 */

public class RouteManager {

	static final String GSON_NAME = "com.google.gson.Gson";
	static final String FAST_JSON_NAME = "com.alibaba.fastjson.JSON";

	/**
	 * 是否为透明状态栏
	 */
	static boolean TRANSPARENR_STATUS = false;

	/**
	 * 默认状态栏颜色
	 */
	static int STATUS_COLOR = -1;

	/**
	 * {@link #TRANSPARENR_STATUS} 设置为true才有效
	 * 设置透明状态栏后 内容是否显示在状态栏之下
	 */
	static boolean STATUS_PADDING_TOP = true;


	/**
	 * 上下文对象
	 */
	private static Context context;

	/**
	 * 防止多次点击情况下 过滤的时间
	 * 即在一定时间内的重复点击 只生效一次
	 * 单位秒
	 */
	private static long filterClickTime = 1;


	public static long getFikterClickTime() {
		return filterClickTime;
	}

	public Context getApplicationContext() {
		return context;
	}


	/**
	 * 一定要在Application 中完成初始化 防止内存泄漏
	 *
	 * @param context
	 * @param options MvpRoute的配置信息
	 * @see RouteOptions
	 */
	public static void init(@NonNull Context context, RouteOptions options) {
		initManager(context, options);
	}

	/**
	 * @param context 上下文对象
	 * @see #init(Context, RouteOptions)
	 */
	public static void init(@NonNull Context context) {
		initManager(context, null);
	}

	/**
	 * 加载配置信息
	 *
	 * @param applicationContext
	 * @param options
	 */
	private static void initManager(Context applicationContext, RouteOptions options) {
		if (options != null) {
			filterClickTime = options.getFilterClickTime();
			TRANSPARENR_STATUS = options.isTransStatus();
			STATUS_COLOR = options.getStatusColor();
			STATUS_PADDING_TOP = options.isStatusPaddingTop();
		}
		context = applicationContext;
		ToastUtils.init(context);
		SpUtils.init(context);
	}


}
