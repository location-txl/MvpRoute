package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:39
 * description：
 */

public interface NetContract {


	interface View extends BaseView {
		void showMessage(String msg);
	}

	abstract class Presenter extends BasePresenter<View> {
		public abstract void loginWanAndroid(String userNmae, String passworld);
	}
}
