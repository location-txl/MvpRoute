package com.location.mvp.mvproutelibrary.scheduler;


import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/4 11:13
 * description：
 */

public  class RxResPonse {
	
	public abstract static class RxGsonResponse<T> implements Function<ResponseBody,T>{
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
		public T apply(ResponseBody responseBody) throws Exception {
			Gson gson = new Gson();
			return gson.fromJson(responseBody.string(),mType);
		}
	}


}
