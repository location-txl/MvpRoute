package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 14:18
 * description：
 */

public interface HomeContract {
	interface View extends BaseView {

	}

	abstract class Presenter extends BasePresenter<View> {

	}
}
