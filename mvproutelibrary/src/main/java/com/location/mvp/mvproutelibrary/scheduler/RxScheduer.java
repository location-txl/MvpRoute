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
package com.location.mvp.mvproutelibrary.scheduler;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxScheduer {


	/**
	 * 在此方法中 默认实现了线程调度 和  异常处理
	 *
	 * @param <T>
	 */
	public static class compose<T> implements ObservableTransformer<T, T> {

		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(new HandlerException<T>());
		}
	}


	public static class HandlerException<T> implements Function<Throwable, Observable<T>> {

		@Override
		public Observable<T> apply(Throwable throwable) throws Exception {
			ExceptionHandle.ResponseThrowable responeThrowable = ExceptionHandle.handleException(throwable);
			return Observable.error(responeThrowable);
		}
	}


}
