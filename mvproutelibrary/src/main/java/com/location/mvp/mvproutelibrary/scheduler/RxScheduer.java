package com.location.mvp.mvproutelibrary.scheduler;

import android.text.TextUtils;

import com.location.mvp.mvproutelibrary.http.IBaseBean;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 20:23
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class RxScheduer {


	/**
	 * 1.0.2版本之后废弃
	 *  新版自定义convertFactory已经解决
	 * @param <T>
	 */
	@Deprecated
	public static class map<T> implements Function<IBaseBean<T>, T> {

		@Override
		public T apply(IBaseBean<T> baseresponse) throws Exception {
			if (!baseresponse.isOk()) {
				String msg = (TextUtils.isEmpty(baseresponse.getErrorMsg()) ? RetrofitClient.getInstance().getErrorResponse() == null ? "not errorMsg" : RetrofitClient.getInstance().getErrorResponse().getErrorMsg(baseresponse.getStatusCode()) : baseresponse.getErrorMsg());
				throw new ExceptionHandle.ServerException(baseresponse.getStatusCode(), msg);
			}
			return baseresponse.getData();
		}
	}


	/**
	 * 线程调度
	 * 1.0.2版本之后废弃
	 * @param <T>
	 */
	@Deprecated
	public static class compose<T> implements ObservableTransformer<IBaseBean<T>, T> {
		@Override
		public ObservableSource<T> apply(Observable upstream) {
			return upstream.map(new map<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}

	/**
	 * 线程调度
	 * @param <T>
	 */
	public static class IO_MAIN<T> implements ObservableTransformer<T, T> {

		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}





	public static class HandlerException<T> implements Function<Throwable, Observable<T>> {

		@Override
		public Observable<T> apply(Throwable throwable) throws Exception {
			ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(throwable);
			return Observable.error(responeThrowable);
		}
	}


}
