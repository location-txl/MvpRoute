package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

import java.io.File;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:39
 * description：
 */

public interface NetContract {


	interface View extends BaseView {
		void loginSuccful(LoginResponse response);

		void showCollectList(CollectListBean response);

		void cleanLoginSuccful();
	}

	abstract class Presenter extends BasePresenter<View> {
		public abstract void loginWanAndroid(String userNmae, String passworld);

		public abstract void cleanLogin();

		public abstract void getCollectList(String page,String username,String passworld);

		public abstract void uploadVideo(File file);
	}
}
