package com.location.mvp.mvproutelibrary.scheduler;


import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 项目:Mvp_Route_Demo
 * author：location
 * time：2018/6/4 11:13
 * description：
 */

public  class RxResPonse {
	
	public  abstract static class RxGsonResponse<T> implements Function<ResponseBody,T>{
		private final Type mType;
		public RxGsonResponse() {
			Type myclass = getClass().getGenericSuperclass();
			if (myclass instanceof Class) {
				throw new RuntimeException("Missing type parameter.");
			}
			ParameterizedType parameter = (ParameterizedType) myclass;
			mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
		}
		@Override
		public final T apply(ResponseBody responseBody) throws Exception {
			Gson gson = new Gson();
			return gson.fromJson(responseBody.string(),mType);
		}
	}
	public static class Compose<T> implements ObservableTransformer<ResponseBody,T> {

		@Override
		public ObservableSource<T> apply(Observable<ResponseBody> upstream) {
			return upstream.map(new RxGsonResponse<T>() {
			}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}


}
