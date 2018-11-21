package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.base.BaseObserver;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.http.cookie.CookiesManager;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;

import java.util.List;

import okhttp3.Cookie;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:41
 * description：
 */

public class NetPresenter extends NetContract.Presenter {

	private final LoginService loginService;

	public NetPresenter() {
		loginService = RetrofitClient.getInstance().createApi(LoginService.class);
	}

	@Override
	public void loginWanAndroid(String userNmae, String passworld) {
		loginService.login(userNmae, passworld)
				.compose(new RxScheduer.compose<LoginResponse>())
				.subscribe(new BaseObserver<LoginResponse>(rxManager, view) {
					@Override
					public void onNext(LoginResponse loginResponse) {
						view.loginSuccful(loginResponse);
					}
				});


	}

	@Override
	public void cleanLogin() {
		SpUtils.getInstance().remove(KeyUtils.USERNAME);
		SpUtils.getInstance().remove(KeyUtils.PASSWORLD);
		SpUtils.getInstance().remove("test");
		CookiesManager.clearAllCookies();
		List<Cookie> cookies = CookiesManager.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.name();
			LogUtils.d("Retrofit",name);
		}

		view.cleanLoginSuccful();

	}

	@Override
	public void getCollectList(String page,String userNmae,String passwrold) {
           loginService.getCollect(page)
				   .compose(new RxScheduer.compose<CollectListBean>())
				   .subscribe(new BaseObserver<CollectListBean>(rxManager,view) {
					   @Override
					   public void onNext(CollectListBean collectListBean) {
					       view.showCollectList(collectListBean);
					   }


				   });
	}
}
