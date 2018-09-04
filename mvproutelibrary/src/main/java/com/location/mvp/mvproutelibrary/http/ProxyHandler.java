package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:52
 * description：
 */

public class ProxyHandler implements InvocationHandler {

	private Object mPrObject;


	private IRefreshToken iRefreshToken;

	public ProxyHandler(Object mPrObject, IRefreshToken iRefreshToken) {
		this.mPrObject = mPrObject;
		this.iRefreshToken = iRefreshToken;
	}

	@Override
	public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		return Observable.just(true)
				.flatMap(new Function<Object, ObservableSource<?>>() {
					@Override
					public ObservableSource<?> apply(Object o) throws Exception {
						return (Observable<?>) method.invoke(mPrObject, args);
					}
				}).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
					@Override
					public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
						return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
							@Override
							public ObservableSource<?> apply(Throwable throwable) throws Exception {

								if (throwable instanceof ExceptionHandle.ServerException) {
									ExceptionHandle.ServerException exception = (ExceptionHandle.ServerException) throwable;
									if (iRefreshToken.isTokenException(exception.result, exception.msg)) {
										synchronized (ProxyHandler.class){
											return iRefreshToken.refreshTokenSuccful();

										}
									} else {
										LogUtils.d("test", "不是异常");
										return Observable.error(throwable);
									}

								} else {
									LogUtils.d("test", "不是异常---");
									return Observable.error(throwable);
								}


							}
						});
					}
				});

	}
}
