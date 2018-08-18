package com.location.mvp.mvproutelibrary.utils;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	private SparseArray<Fragment> sparseIntArray;


	private String simpleName;


	private static FragmentManager fragmentManager;

	private static FragmentUtils instance;


	private FragmentTransaction transaction;

	private Fragment baseFragment;


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
	public static FragmentUtils getInstance(FragmentActivity activity) {
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
	public static FragmentUtils getInstance(Fragment fragment) {
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
	public FragmentWrapper start(Class<? extends Fragment> clazz) {
		transaction = fragmentManager.beginTransaction();
		simpleName = clazz.getSimpleName();
		baseFragment =  fragmentManager.findFragmentByTag(simpleName);
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
	public FragmentWrapper start(Class<? extends Fragment> clazz, String tag) {
		transaction = fragmentManager.beginTransaction();
		if (TextUtils.isEmpty(tag)) {
			simpleName = clazz.getSimpleName();
		} else {
			simpleName = tag;
		}
		baseFragment =  fragmentManager.findFragmentByTag(simpleName);
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
	 * 隐藏某个fragmnet
	 *
	 * @param clazz 取类名作为tag
	 */
	public void hideFragmnet(Class<? extends Fragment> clazz) {
		transaction = fragmentManager.beginTransaction();
		if (fragmentManager.findFragmentByTag(clazz.getSimpleName()) != null) {
			transaction.hide(fragmentManager.findFragmentByTag(clazz.getSimpleName()));
		}
	}

	/**
	 * 隐藏某个fragmnet
	 *
	 * @param tag tag名
	 */
	public void hideFragmnet(String tag) {
		transaction = fragmentManager.beginTransaction();
		if (fragmentManager.findFragmentByTag(tag) != null) {
			transaction.hide(fragmentManager.findFragmentByTag(tag));
		}
	}

	/**
	 * 显示某个fragmnet
	 *
	 * @param clazz
	 */
	public void showFragment(Class<? extends Fragment> clazz) {
		transaction = fragmentManager.beginTransaction();
		if (fragmentManager.findFragmentByTag(clazz.getSimpleName()) != null) {
			transaction.show(fragmentManager.findFragmentByTag(clazz.getSimpleName()));
		}
	}

	/**
	 * 显示某个fragment
	 *
	 * @param tag
	 */
	public void showFragment(String tag) {
		transaction = fragmentManager.beginTransaction();
		if (fragmentManager.findFragmentByTag(tag) != null) {
			transaction.show(fragmentManager.findFragmentByTag(tag));
		}
	}

	/**
	 * 查找fragmnet
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T extends Fragment> T findFragment(Class<? extends T> clazz) {

		return (T) fragmentManager.findFragmentByTag(clazz.getSimpleName());
	}


	/**
	 * 获取所有fragment  不会查询底部嵌套fragment
	 *
	 * @return
	 */
	public List<Fragment> getAllFragments() {
		List<Fragment> fragmnets = getFragmnets(fragmentManager);
		return fragmnets;
	}

	/**
	 * 获取所有fragment 会查询fragmnet内部包含的fragment
	 *
	 * @return
	 */
	public List<FragmentModle> getAllModleFragments() {
		return getAllModleFragments(fragmentManager);
	}

	private List<FragmentModle> getAllModleFragments(FragmentManager fragmentManager) {
		List<Fragment> fragmnets = getFragmnets(fragmentManager);
		List<FragmentModle> result = new ArrayList<>();

		for (int i = fragmnets.size() - 1; i >= 0; i--) {
			Fragment fragment = fragmnets.get(i);
			if (fragment != null) {
				result.add(new FragmentModle(fragment, getAllModleFragments(fragment.getChildFragmentManager())));
			}
		}
		return result;
	}


	private List<Fragment> getFragmnets(FragmentManager fragmentManager) {
		List<Fragment> fragments = fragmentManager.getFragments();
		if (fragments == null || fragments.isEmpty()) {
			return new ArrayList<>();
		}
		return fragments;
	}

	/**
	 * 查找fragmnet
	 *
	 * @param tag
	 * @param <T>
	 * @return
	 */
	public <T extends Fragment> T findFragment(String tag) {
		return (T) fragmentManager.findFragmentByTag(tag);
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
		/**
		 * 设置Fragmnet 转场动画
		 *
		 * @param startAn
		 * @param exitan
		 * @return
		 */
		public FragmentWrapper addAnim(@AnimRes @AnimatorRes int startAn, @AnimRes @AnimatorRes int
				exitan) {
			transaction.setCustomAnimations(startAn, exitan);
			return this;
		}

		public FragmentWrapper addAnimStart(@AnimRes @AnimatorRes int startAn) {
			transaction.setCustomAnimations(startAn, 0);
			return this;
		}

		public FragmentWrapper addAnimEnd(@AnimRes @AnimatorRes int endAn) {
			transaction.setCustomAnimations(0, endAn);
			return this;
		}

		/**
		 * 设置背景色
		 *
		 * @param color
		 * @return
		 */
		public FragmentWrapper setBackGroundColor(@ColorInt int color) {
			View view = baseFragment.getView();
			if (view != null) {
				view.setBackgroundColor(color);
			}
			return this;
		}

		/**
		 * 设置背景资源
		 *
		 * @param drawid
		 * @return
		 */
		public FragmentWrapper setBackgroundResource(@DrawableRes int drawid) {

			View view = baseFragment.getView();
			if (view != null) {
				view.setBackgroundResource(drawid);
			}
			return this;
		}

		/**
		 * 设置背景
		 *
		 * @param drawable
		 * @return
		 */
		public FragmentWrapper setBackGroundDrawable(Drawable drawable) {
			View view = baseFragment.getView();
			if (view != null) {
				view.setBackground(drawable);
			}
			return this;
		}

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
		 *
		 * @param bundle
		 * @return
		 */
		public FragmentResponse setBundle(@NonNull Bundle bundle) {
			if (!baseFragment.isAdded()) {
				baseFragment.setArguments(bundle);
			} else {
				Bundle arguments = baseFragment.getArguments();
				if (arguments != null) {
					arguments.putAll(bundle);
				}

			}
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

	public class FragmentModle {
		private Fragment fragment;
		private List<FragmentModle> fragments;

		public FragmentModle(Fragment fragment, List<FragmentModle> fragments) {
			this.fragment = fragment;
			this.fragments = fragments;
		}

		public Fragment getFragment() {
			return fragment;
		}

		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}

		public List<FragmentModle> getFragments() {
			return fragments;
		}

		public void setFragments(List<FragmentModle> fragments) {
			this.fragments = fragments;
		}
	}
}
