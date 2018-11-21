package com.location.mvp.mvp_route_demo.contract;


import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.BaseView;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 18:33
 * description：
 */

public interface SpanContract {
	public interface View extends BaseView {
		void showMaxText(CharSequence charSequence);

		void showBackText(CharSequence charSequence);

		void showColorText(CharSequence charSequence);

		void showClickText(CharSequence charSequence);
	}

	public abstract class Presenter extends BasePresenter<View> {
		public abstract void setMaxtext(String message,float textsize);

		public abstract int randomColor();

		public abstract void setBackTextColor(String message);

		public abstract void setForgetTextColor(String message);

		public abstract  void setSpanClick(String message);


	}
}
