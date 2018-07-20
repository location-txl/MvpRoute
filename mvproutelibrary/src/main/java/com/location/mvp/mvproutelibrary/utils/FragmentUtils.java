package com.location.mvp.mvproutelibrary.utils;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BaseFragment;

/**
 * 项目:Mvp_Route_Demo
 * author：location
 * time：2018/5/24 13:12
 * description：
 *
 * @author Administrator
 */

public class FragmentUtils {
	private static final String TAG = FragmentUtils.class.getSimpleName();
	/**
	 * 存储fragment
	 * 键值对  key----布局id
	 * value---fragment
	 */
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


	/**
	 * 对应的activity  用于开启管理者
	 *
	 * @param activity
	 * @return
	 */
	public static FragmentUtils getInstance(BaseActivity activity) {
		newInstance();
		fragmentManager = activity.getSupportFragmentManager();
		return instance;
	}

	/**
	 * 对应的fragment  用于开启管理者
	 *
	 * @param fragment
	 * @return
	 */
	public static FragmentUtils getInstance(BaseFragment fragment) {
		newInstance();
		fragmentManager = fragment.getChildFragmentManager();
		return instance;
	}


	/**
	 * 开启Fragment
	 *
	 * @param clazz
	 * @return
	 */
	public FragmentWrapper start(Class<? extends BaseFragment> clazz) {
		transaction = fragmentManager.beginTransaction();
		simpleName = clazz.getSimpleName();
		baseFragment = (BaseFragment) fragmentManager.findFragmentByTag(simpleName);
		if (baseFragment == null) {
			try {
				baseFragment = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException("instantiation error \n" + e.getMessage());
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Type conversion error \n" + e.getMessage());
			}
		}
		return new FragmentWrapper();
	}

	/**
	 * 开启Fragment
	 *
	 * @param clazz
	 * @return
	 */
	public FragmentWrapper start(Class<? extends BaseFragment> clazz, String tag) {
		transaction = fragmentManager.beginTransaction();
		if (TextUtils.isEmpty(tag)) {
			simpleName = clazz.getSimpleName();
		} else {
			simpleName = tag;
		}
		baseFragment = (BaseFragment) fragmentManager.findFragmentByTag(simpleName);
		if (baseFragment == null) {
			try {
				baseFragment = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException("instantiation error \n" + e.getMessage());
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Type conversion error \n" + e.getMessage());
			}
		}
		return new FragmentWrapper();
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


	public class FragmentWrapper {


		public FragmentResponse add(@IdRes int ids) {
			resId = ids;
			if (!baseFragment.isAdded()) {
				transaction.add(resId, baseFragment, simpleName);
			}

			if (sparseIntArray.get(resId) != null) {
				transaction.hide(sparseIntArray.get(resId));
			}
			transaction.show(baseFragment);
			return new FragmentResponse();
		}

		public void replace(@IdRes int ids) {
//			resId = ids;
//			transaction.replace(ids, baseFragment, simpleName);
		}
	}

	public class FragmentResponse {

		/**
		 * 加入回退栈
		 *
		 * @return
		 */
		public FragmentResponse addToBack() {
			transaction.addToBackStack(simpleName);
			return this;
		}

		/**
		 * 传递数据
		 * 如果检测到Fragment已经创建则  不会传递数据
		 *
		 * @param bundle
		 * @return
		 */
		public FragmentResponse setBundle(@NonNull Bundle bundle) {
			if (!baseFragment.isAdded()) {
				baseFragment.setArguments(bundle);
			} else {
			}
			return this;
		}

		/**
		 * 设置Fragmnet 转场动画
		 *
		 * @param startAn
		 * @param exitan
		 * @return
		 */
		public FragmentResponse addAnim(@AnimRes @AnimatorRes int startAn, @AnimRes @AnimatorRes int
				exitan) {
			transaction.setCustomAnimations(startAn, exitan);
			return this;
		}

		public FragmentResponse addAnimStart(@AnimRes @AnimatorRes int startAn) {
			transaction.setCustomAnimations(startAn, 0);
			return this;
		}

		public FragmentResponse addAnimEnd(@AnimRes @AnimatorRes int endAn) {
			transaction.setCustomAnimations(0, endAn);
			return this;
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


}
