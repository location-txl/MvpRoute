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
package com.location.mvp.mvproutelibrary.Base;

import com.location.mvp.mvproutelibrary.manager.RxManager;

/**
 * mvp 模式 Presenter层接口继承这个类
 *
 * @param <T>
 */


public abstract class BasePresenter<T extends BaseView> {
	protected RxManager rxManager;
	protected T view;


	/**
	 * 注册
	 */
	void unRegist() {
		rxManager.clear();
		view = null;
	}

	/**
	 * 解绑
	 * @param view
	 */
	 void regist(T view) {
		this.view = view;
		rxManager = new RxManager();
	}
}
