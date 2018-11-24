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

import android.os.Looper;
import android.support.annotation.IntRange;
import android.view.View;

import com.location.mvp.mvproutelibrary.base.RouteManager;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 对rxjava分装的工具类
 * {@link #countDown(View, int, CountDownListener)} rxJava的倒计时工具
 * <br>
 * {@link #click(View.OnClickListener, View...)} rxJava的防止view多次点击
 * 默认1秒内不允许重复点击
 * <br>
 * 网络模块{@linkplain compose 调度器}  调度器包含 线程的切换 网络异常的统一处理
 * <br>
 * <br>
 * 网络模块  异常处理  {@link HandlerException}
 */
public class RxScheduer {


	/**
	 * 倒计时
	 *
	 * @param view     倒计时所用到的view
	 * @param second   倒计时时长  单位 秒
	 * @param listener 倒计时回调
	 * @param <T>
	 * @return Disposable  返回 Disposable  在Activity的onDestroy方法中
	 * disposable.dispose() 取消掉  防止内存泄漏
	 * @see CountDownListener  回调接口
	 */
	public static <T extends View> Disposable countDown(final T view, @IntRange(from = 1) final int second, final CountDownListener<T> listener) {
		if (listener == null || second <= 0) return null;
		return Flowable.intervalRange(0, second + 1, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
				.doOnNext(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						listener.onCountDownProgress(view, (int) (second - aLong));
					}
				}).doOnComplete(new Action() {
					@Override
					public void run() throws Exception {
						listener.onCountDownComplete(view);
					}
				}).doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
						listener.onBindCountDown(view);
					}
				}).subscribe();

	}

	/**
	 * 防止 view多次点击  默认1秒内只允许点击一次
	 *
	 * @param views     需要点击的view  接受一个可变参数
	 * @param listener 点击view的回调
	 * @throws IllegalStateException 当操作的时候在子线程的时候会抛出此异常
	 */
	public static void click( final View.OnClickListener listener, View... views) {
		if (Looper.getMainLooper() != Looper.myLooper()) {
			throw new IllegalStateException("Click must be executed in the main thread,you thread in " + Thread.currentThread().getName());
		}
		if (views == null ||views.length<=0|| listener == null) return;
		for (View view : views) {
			Observable.create(new ViewObservable(view))
					.throttleFirst(RouteManager.getFikterClickTime(), TimeUnit.SECONDS)
					.subscribe(new Consumer<View>() {
						@Override
						public void accept(View view) throws Exception {
							listener.onClick(view);
						}
					});
		}

	}


	private static class ViewObservable implements ObservableOnSubscribe<View> {
		private View view;

		ViewObservable(View view) {
			this.view = view;
		}

		@Override
		public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!emitter.isDisposed()) {
						emitter.onNext(view);
					}
				}
			};
			view.setOnClickListener(listener);
		}
	}

	/**
	 * 在此方法中 默认实现了线程调度 和  异常处理
	 *
	 * @param <T>
	 */
	public static class compose<T> implements ObservableTransformer<T, T> {

		@Override
		public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(new HandlerException<T>());
		}
	}


	/**
	 * 网络请求异常处理
	 *
	 * @param <T>
	 */
	public static class HandlerException<T> implements Function<Throwable, Observable<T>> {

		@Override
		public Observable<T> apply(Throwable throwable) throws Exception {
			ExceptionHandle.ResponseThrowable responseThrowable = ExceptionHandle.handleException(throwable);
			return Observable.error(responseThrowable);
		}
	}

	/**
	 * 倒计时 回调
	 * {@link RxScheduer#countDown(View, int, CountDownListener)}
	 *
	 * @param <T>
	 */
	public interface CountDownListener<T extends View> {
		/**
		 * 倒计时 与view绑定时的操作   只会调用一次
		 * 在这里可以对view的Enabled 字体颜色  做操作
		 *
		 * @param view
		 */
		void onBindCountDown(T view);

		/**
		 * 倒计时 进行中  会多次调用
		 *
		 * @param view
		 * @param second 当前剩余的秒数
		 */
		void onCountDownProgress(T view, int second);

		/**
		 * 倒计时 完成  只会调用一次
		 * 这里可以恢复view的状态等
		 *
		 * @param view
		 */
		void onCountDownComplete(T view);
	}

}
