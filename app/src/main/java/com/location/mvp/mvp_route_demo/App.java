package com.location.mvp.mvp_route_demo;

import android.app.Application;

import com.location.mvp.mvp_route_demo.base.BaseData;
import com.location.mvp.mvp_route_demo.base.ErrorResponse;
import com.location.mvp.mvp_route_demo.base.LoadingView;
import com.location.mvp.mvp_route_demo.base.RefreshToken;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.http.RetrofitConfig;
import com.location.mvp.mvproutelibrary.http.cookie.CookiesManager;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;
//import com.squareup.leakcanary.LeakCanary;

import java.time.temporal.ValueRange;

import okhttp3.OkHttpClient;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/7/2 0002 20:37
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
//		RetrofitConfig config = new RetrofitConfig("http://www.wanandroid.com/");
		RetrofitConfig config = new RetrofitConfig("http://hb5.api.okayapi.com/");
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.cookieJar(new CookiesManager(this));
		config.setErrorResponse(new ErrorResponse());
		config.setiRefreshToken(new RefreshToken());
		config.setGsonClass(BaseData.class);
		config.setBuilder(builder);
//		config.setLodingView(new LoadingView());
		RetrofitClient.init(config);
		ToastUtils.init(this);
		SpUtils.init(this);
		new LogUtils.LogUtilsBuilder().setPrintClass(false).setPrintLine(false);
//		if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
//		LeakCanary.install(this);
	}


}
