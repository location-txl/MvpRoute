package com.location.mvp.mvp_route_demo.contract;


import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.BaseView;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/14 11:23
 * description：
 */

public interface SpContract {
	interface View extends BaseView {
	  void showMessage(String message);
	}

	public abstract class Presenter extends BasePresenter<View> {
		public abstract void save(String key, String value);

		public abstract void delete(String key);

	}

}
