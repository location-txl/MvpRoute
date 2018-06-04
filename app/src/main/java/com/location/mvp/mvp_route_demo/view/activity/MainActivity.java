package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.presenter.MainPresenter;
import com.location.mvp.mvp_route_demo.view.fragment.TestFragment;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

public class MainActivity extends BaseActivity<MainPresenter> {


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        TestFragment testFragment = new TestFragment();
        fragmentTransaction.add(R.id.id_fre, testFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void loadData() {

    }

    @NonNull
    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @Override
    public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

    }
}
