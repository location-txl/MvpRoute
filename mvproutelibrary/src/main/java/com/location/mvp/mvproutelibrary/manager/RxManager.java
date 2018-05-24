package com.location.mvp.mvproutelibrary.manager;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 19:25
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class RxManager {
    private final CompositeDisposable compositeDisposable;

    public RxManager() {
        compositeDisposable = new CompositeDisposable();
    }

    public void add(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }
    public void clear(){
        compositeDisposable.clear();
    }
}
