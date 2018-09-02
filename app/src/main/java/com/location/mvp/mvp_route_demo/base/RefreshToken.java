package com.location.mvp.mvp_route_demo.base;

import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.IBaseBean;
import com.location.mvp.mvproutelibrary.http.IRefreshToken;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.SpUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.xml.parsers.FactoryConfigurationError;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:23
 * description：
 */

public class RefreshToken implements IRefreshToken{


	@Override
	public boolean refreshTokenSuccful() {
		final boolean[] succful = new boolean[1];
		LoginService api = RetrofitClient.getInstance().createApi(LoginService.class);
api.login("tianxiaolong","tianxiaolong")
		.map(new RxScheduer.map<LoginResponse>())
		.onErrorResumeNext(new RxScheduer.HandlerException<LoginResponse>())
		.compose(new RxScheduer.IO_MAIN<LoginResponse>())
		.subscribe(new Observer<LoginResponse>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(LoginResponse response) {
				SpUtils.getInstance().putValue(KeyUtils.USERNAME, response.getUsername());
				SpUtils.getInstance().putValue(KeyUtils.PASSWORLD,response.getPassword());
				succful[0] = true;
			}

			@Override
			public void onError(Throwable e) {
    succful[0] = false;
			}

			@Override
			public void onComplete() {

			}
		});
		return succful[0];
	}

	@Override
	public boolean isTokenException(int code, String errorMsg) {
		if("请先登录！".equals(errorMsg)){
			return true;
		}
		return false;
	}
}
