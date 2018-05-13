package com.location.mvp.mvproutelibrary.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 19:00
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected T presenter;
    protected final String TAG = getClass().getSimpleName();
    private BaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        if (presenter != null) {

            presenter.regist(this);
        }
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            getBundle(getArguments());
        }
        initView(view);
        loadData();
    }

    protected void getBundle(Bundle bundle) {
    }

    protected abstract void initView(View view);

    protected abstract void loadData();

    protected abstract @NonNull
    T createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.unRegist();
        }
    }

    protected abstract @LayoutRes
    int getLayout();


    protected void onShow() {
    }

    protected void onHide() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHide();
        } else {
            onShow();
        }
    }
}
