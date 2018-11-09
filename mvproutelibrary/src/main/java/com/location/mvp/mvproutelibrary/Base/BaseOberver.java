package com.location.mvp.mvproutelibrary.Base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.http.INetWorkLoadingView;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.manager.RxManager;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 21:49
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseOberver<T> implements Observer<T> {
	private RxManager rxManager;
	private BaseView baseView;
	private INetWorkLoadingView loadingView;

	public BaseOberver(RxManager rxManager, BaseView baseView) {
		this.rxManager = rxManager;
		this.baseView = baseView;
		if (RetrofitClient.getInstance().getLoadingView() != null) {
			loadingView = RetrofitClient.getInstance().getLoadingView();
			loadingView.createLoadingView(baseView instanceof AppCompatActivity ? (AppCompatActivity) baseView : ((Fragment) baseView).getActivity());
		}
	}

	@Override
	public void onSubscribe(Disposable d) {
		rxManager.add(d);
		if (baseView instanceof BaseActivity && ((BaseActivity) baseView).isFinishing()) {
			rxManager.clear();
		} else if (loadingView != null) {
			loadingView.showLoading();
		}
	}

	@Override
	public void onError(Throwable e) {
		if (e instanceof ExceptionHandle.ResponeThrowable) {
			baseView.onshowError((ExceptionHandle.ResponeThrowable) e);
		}
		if (loadingView != null) loadingView.dismissLoading();
	}

	@Override
	public void onComplete() {
		if (loadingView != null) loadingView.dismissLoading();
	}
}
