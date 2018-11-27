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

/**
 * @author location
 *         MvpRoute  配置信息 用于{@link RouteManager} 初始化
 * @see RouteManager#init(Context, RouteOptions)
 * @see RouteManager#init(Context)
 */

public class RouteOptions {
	private long filterClickTime;

	private boolean transStatus = false;

	private int statusColor = -1;

	private boolean statusPaddingTop = true;


	long getFilterClickTime() {
		return filterClickTime;
	}

	/**
	 * 设置 过滤view点击的时间
	 *
	 * @param filterClickTime
	 * @see RouteManager#filterClickTime
	 */
	public void setFilterClickTime(long filterClickTime) {
		this.filterClickTime = filterClickTime;
	}

	boolean isTransStatus() {
		return transStatus;
	}

	/**
	 * 设置是否是透明状态栏
	 *
	 * @param transStatus
	 * @see RouteManager#TRANSPARENR_STATUS
	 */
	public void setTransStatus(boolean transStatus) {
		this.transStatus = transStatus;
	}

	int getStatusColor() {
		return statusColor;
	}

	/**
	 * 设置透明状态栏颜色
	 *
	 * @param statusColor
	 * @see RouteManager#STATUS_COLOR
	 */
	public void setStatusColor(int statusColor) {
		this.statusColor = statusColor;
	}

	boolean isStatusPaddingTop() {
		return statusPaddingTop;
	}

	/**
	 * 设置透明状态栏后  内容是否显示在状态栏之下  默认true
	 *
	 * @param statusPaddingTop
	 * @see RouteManager#STATUS_PADDING_TOP
	 */
	public void setStatusPaddingTop(boolean statusPaddingTop) {
		this.statusPaddingTop = statusPaddingTop;
	}
}
