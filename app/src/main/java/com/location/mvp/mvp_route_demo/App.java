package com.location.mvp.mvp_route_demo;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.location.mvp.mvp_route_demo.base.BaseData;
import com.location.mvp.mvp_route_demo.base.ErrorResponse;
import com.location.mvp.mvp_route_demo.base.LoadingView;
import com.location.mvp.mvp_route_demo.base.RefreshToken;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvproutelibrary.base.RouteManager;
import com.location.mvp.mvproutelibrary.base.RouteOptions;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.http.RetrofitConfig;
import com.location.mvp.mvproutelibrary.http.cookie.CookiesManager;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;
//import com.squareup.leakcanary.LeakCanary;

import java.time.temporal.ValueRange;
import java.util.List;

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
	private static App context;
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		//实例化Config对象  设置Retrofit baseUrl
		RetrofitConfig config = new RetrofitConfig("http://www.wanandroid.com/");
//		RetrofitConfig config = new RetrofitConfig("http://hb5.api.okayapi.com/");
		//设置自定义的Okhttpclient 可选
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.cookieJar(new CookiesManager(this));
		config.setBuilder(builder);
		//设置异常解析类  可选
		config.setErrorResponse(new ErrorResponse());
		//设置刷新token类  可选
		config.setiRefreshToken(new RefreshToken());
		//设置解析父类 必选
		config.setGsonClass(BaseData.class);
		//设置网络请求时弹出的dialog 可选
		config.setLodingView(new LoadingView());
		//初始化
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
		//使用默认配置
		RouteManager.init(this);

		//使用自定义配置
		RouteOptions options = new RouteOptions();
		//设置view点击的过滤时间为2秒
		options.setFilterClickTime(2);
		//设置全局沉浸式状态栏
		options.setTransStatus(true);
		//设置在沉浸式状态栏下布局内容在状态栏之下
		options.setStatusPaddingTop(true);
		RouteManager.init(this,options);
	}

	private static Context getApp(){
		return context;
	}

}
