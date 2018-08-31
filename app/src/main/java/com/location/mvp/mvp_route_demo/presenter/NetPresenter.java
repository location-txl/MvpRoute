package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;

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
				.map(new RxScheduer.map<LoginResponse>())
				.onErrorResumeNext(new RxScheduer.HandlerException<LoginResponse>())
				.compose(new RxScheduer.IO_MAIN<LoginResponse>())
				.subscribe(new BaseOberver<LoginResponse>(rxManager, view) {
					@Override
					public void onNext(LoginResponse loginResponse) {
						view.showMessage("登录成功");
					}
				});


	}
}
