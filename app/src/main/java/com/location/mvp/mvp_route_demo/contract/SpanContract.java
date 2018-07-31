package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 18:33
 * description：
 */

public interface SpanContract {
	public interface View extends BaseView {
		void showMaxText(CharSequence charSequence);

	}

	public abstract class Presenter extends BasePresenter<View> {
		public abstract void setMaxtext(String message,float textsize);


	}
}
