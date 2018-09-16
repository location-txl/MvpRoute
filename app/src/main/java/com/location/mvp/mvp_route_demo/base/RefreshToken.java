package com.location.mvp.mvp_route_demo.base;

import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.IBaseBean;
import com.location.mvp.mvproutelibrary.http.IRefreshToken;
import com.location.mvp.mvproutelibrary.http.ProxyHandler;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.parsers.FactoryConfigurationError;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import okhttp3.MultipartBody;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:23
 * description：
 */

public class RefreshToken implements IRefreshToken {
	@Override
	public synchronized Observable refreshTokenSuccful() {
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
