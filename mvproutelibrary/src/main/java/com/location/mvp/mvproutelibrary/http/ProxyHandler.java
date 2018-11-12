/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.TimeUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 *   动态代理类  这里处理 token失效问题
 *   没有配置 刷新token类  此类不生效
 *   刷新token类  {@link IRefreshToken}
 */

public class ProxyHandler implements InvocationHandler {

	private Object mPrObject;


	private IRefreshToken iRefreshToken;
	private long refreshTime;

	public ProxyHandler(IRefreshToken iRefreshToken) {
		this.iRefreshToken = iRefreshToken;
	}


	public void setObject(Object object) {
		this.mPrObject = object;
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

										synchronized (ProxyHandler.class) {
                                              if((System.currentTimeMillis()-refreshTime)<1000){

												  return Observable.timer(50, TimeUnit.MILLISECONDS);
											  }else{
                                                  refreshTime = System.currentTimeMillis();
												  return iRefreshToken.refreshToken();
											  }

										}
									} else {
										return Observable.error(throwable);
									}

								} else {
									return Observable.error(throwable);
								}


							}
						});
					}
				});

	}
}
