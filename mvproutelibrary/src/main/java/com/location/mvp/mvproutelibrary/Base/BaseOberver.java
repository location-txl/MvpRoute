package com.location.mvp.mvproutelibrary.Base;

import android.util.Log;

import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.manager.RxManager;

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

    public BaseOberver(RxManager rxManager, BaseView baseView) {
        this.rxManager = rxManager;
        this.baseView = baseView;
    }

    @Override
    public void onSubscribe(Disposable d) {
        rxManager.add(d);
        if(baseView instanceof BaseActivity && ((BaseActivity) baseView).isFinishing()){
            rxManager.clear();
        }
    }

    @Override
    public void onError(Throwable e) {
        baseView.onshowError((ExceptionHandle.ResponeThrowable) e);
    }

    @Override
    public void onComplete() {

    }
}
