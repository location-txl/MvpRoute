package com.location.mvp.mvp_route_demo.contract;

import android.content.Context;

import com.location.mvp.mvp_route_demo.view.activity.ResultActivity;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:02
 * description：
 */

public interface ResultContract {
	interface View extends BaseView {

		void showMessage(String message);

		void showSince(CharSequence since);
	}

	public abstract class Presenter extends BasePresenter<View> {

		public abstract void initSince(String since);

		public abstract void startActivity(ResultActivity context, int RequestCode);
	}


}
