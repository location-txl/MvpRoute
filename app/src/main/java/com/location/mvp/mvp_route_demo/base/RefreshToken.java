package com.location.mvp.mvp_route_demo.base;

import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.http.IRefreshToken;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;

import io.reactivex.Observable;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:23
 * description：
 */

public class RefreshToken implements IRefreshToken {
	@Override
	public synchronized Observable refreshToken() {
			LoginService api = RetrofitClient.getInstance().createRefreshToken(LoginService.class);
			return api.login("tianxiaolong", "123");

	}



	@Override
	public boolean isTokenException(int code, String errorMsg) {
		if ("请先登录！".equals(errorMsg)) {
			return true;
		}
		return false;
	}
}
