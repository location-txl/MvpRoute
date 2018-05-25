package com.location.mvp.mvproutelibrary.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 20:11
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected T presenter;
protected final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        presenter = createPresenter();
        if(presenter!=null){
            presenter.regist(this);
        }
        initView();
        loadData();
    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void initView();

    protected abstract void loadData();

    protected abstract @NonNull
    T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.unRegist();
        }
    }
}
