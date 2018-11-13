package com.location.mvp.mvproutelibrary.Base;

import com.location.mvp.mvproutelibrary.manager.RxManager;

import io.reactivex.disposables.Disposable;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/13 13:34
 * description：
 */

public abstract class BaseProgressObserver<T> extends BaseObserver<T> {
	private boolean isComptle;
	private long contentLength;
	private Disposable disposable;

	public Disposable getDisposable() {
		return disposable;
	}

	public BaseProgressObserver(RxManager rxManager, BaseView baseView) {
		super(rxManager, baseView);
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public void onSubscribe(Disposable d) {
		super.onSubscribe(d);
		this.disposable = d;
	}

	@Override
	public void onNext(T t) {
		uploadSuccful(t, contentLength);
	}

	public abstract void onProgress(long readWrite, long contentLength);

	public abstract void uploadSuccful(T response, long contentLength);
}
