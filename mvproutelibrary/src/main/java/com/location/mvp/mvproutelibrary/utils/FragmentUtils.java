package com.location.mvp.mvproutelibrary.utils;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;

import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BaseFragment;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 13:12
 * description：
 */

public class FragmentUtils {
    private static final String TAG = FragmentUtils.class.getSimpleName();
    private SparseArray<BaseFragment> sparseIntArray;

    private String simpleName;


    private static FragmentManager fragmentManager;

    private static FragmentUtils instance;


    private FragmentTransaction transaction;

    private BaseFragment baseFragment;


    private @IdRes
    int resId;

    private FragmentUtils() {
        sparseIntArray = new SparseArray<>();
    }

    public static FragmentUtils getInstance(BaseActivity activity) {
        newInstance();
        fragmentManager = activity.getSupportFragmentManager();
        return instance;
    }

    public static FragmentUtils getInstance(BaseFragment fragment) {
        newInstance();
        fragmentManager = fragment.getChildFragmentManager();
        return instance;
    }


    public FragmentUtils start(Class<? extends BaseFragment> clazz) {

        transaction = fragmentManager.beginTransaction();
        simpleName = clazz.getSimpleName();
        baseFragment = (BaseFragment) fragmentManager.findFragmentByTag(simpleName);
        if (baseFragment == null) {
            try {
                baseFragment = clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("实例化失败");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("类型转换异常");
            }
        }
        return this;
    }


    public FragmentUtils add(@IdRes int resid) {
        Log.d(TAG, "isadd===>" + baseFragment.isAdded());
        this.resId = resid;
        if (!baseFragment.isAdded()) {
            transaction.add(resid, baseFragment, simpleName);
        }

        if (sparseIntArray.get(resid) != null) {
            transaction.hide(sparseIntArray.get(resid));
        }
        transaction.show(baseFragment);
        return this;
    }


    public FragmentUtils addToBack() {
        transaction.addToBackStack(simpleName);
        return this;
    }

    public FragmentUtils addAnim(@AnimRes @AnimatorRes int startAn, @AnimRes @AnimatorRes int
            exitan) {
        transaction.setCustomAnimations(startAn, exitan);
        return this;
    }
    public FragmentUtils addAnimStart(@AnimRes @AnimatorRes int startAn) {
        transaction.setCustomAnimations(startAn, 0);
        return this;
    }
    public FragmentUtils addAnimEnd(@AnimRes @AnimatorRes int endAn) {
        transaction.setCustomAnimations(0, endAn);
        return this;
    }

    public FragmentUtils setBundle(@NonNull Bundle bundle) {
        if (!baseFragment.isAdded()) {
            baseFragment.setArguments(bundle);
        }
        return this;
    }

    private static void newInstance() {
        if (instance == null) {
            synchronized (FragmentUtils.class) {
                if (instance == null) {
                    instance = new FragmentUtils();
                }
            }
        }
    }

    public void commit() {
        transaction.commit();
        sparseIntArray.put(resId, baseFragment);
        baseFragment = null;
        simpleName = null;
        resId = -1;
        transaction = null;
        fragmentManager = null;
    }

}
