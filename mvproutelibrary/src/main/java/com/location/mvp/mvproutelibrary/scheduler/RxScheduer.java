package com.location.mvp.mvproutelibrary.scheduler;

import com.location.mvp.mvproutelibrary.Base.BaseBean;
import com.location.mvp.mvproutelibrary.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 20:23
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class RxScheduer {


	public static class map<T> implements Function<BaseBean<T>, T> {

		@Override
		public T apply(BaseBean<T> tBaseBean) throws Exception {
			return tBaseBean.getData();
		}
	}

	public static class compose<T> implements ObservableTransformer<BaseBean<T>, T> {
		@Override
		public ObservableSource<T> apply(Observable upstream) {
			return upstream.map(new Function<BaseBean<T>, T>() {
				@Override
				public T apply(BaseBean<T> tBaseBean) throws Exception {
					return tBaseBean.getData();
				}
			}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}

	public static class IO_MAIN<T> implements ObservableTransformer<T, T> {

		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}


	public static <T> Function<Throwable, ObservableSource<T>> handlerException() {
		return new Function<Throwable, ObservableSource<T>>() {
			@Override
			public ObservableSource<T> apply(Throwable throwable) throws Exception {
				return Observable.error(throwable);
			}
		};
	}


}
