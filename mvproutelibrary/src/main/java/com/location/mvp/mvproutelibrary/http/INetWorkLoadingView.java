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
package com.location.mvp.mvproutelibrary.http;

import android.content.Context;

/**
 * 加载弹窗接口
 * 实现此接口  在网络请求过程中
 * 会弹出此弹窗
 */

public interface INetWorkLoadingView {


	/**
	 * 初始化弹窗
	 *
	 * @param context
	 */
	void createLoadingView(Context context);

	/**
	 * 显示弹窗
	 */
	void showLoading();


	/**
	 * 关闭弹窗
	 */
	void dismissLoading();
}
