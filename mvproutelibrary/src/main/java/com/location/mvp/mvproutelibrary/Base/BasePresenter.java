package com.location.mvp.mvproutelibrary.Base;

import com.location.mvp.mvproutelibrary.manager.RxManager;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 19:18
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BasePresenter<T extends BaseView> {
	protected RxManager rxManager;
	protected T view;



	protected void unRegist() {
		rxManager.clear();
	}

	protected void regist(T view) {
		this.view = view;
		rxManager = new RxManager();
	}
}
