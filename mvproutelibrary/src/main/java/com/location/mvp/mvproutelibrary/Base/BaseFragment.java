/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.location.mvp.mvproutelibrary.utils.KeyBoardUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.location.mvp.mvproutelibrary.base.Request.EXERA_REQUEST;
import static com.location.mvp.mvproutelibrary.base.Request.EXERA_RESULT;


/**
 * 基类  BaseFragment
 * 设置{@link Layout} 注解来绑定布局
 * 设置{@link InjectBundle}  注解来注入字段  无需手写bundle get方法
 * 设置{@link Request}       注解来设置onActivityResult回调方法  无需手动重写{@link #onActivityResult(int, int, Intent)}
 * @param <T>
 * @author Administrator
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
	protected T presenter;
	protected final String TAG = getClass().getSimpleName();
	protected FragmentActivity activity;
	protected View rootView;


	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static <T extends BaseFragment> T newInstance(Class<? extends T> clazz) {
		try {
			return clazz.newInstance();
		} catch (java.lang.InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static <T extends BaseFragment> T newInstance(Class<? extends T> clazz, Bundle bundle) {
		try {
			T t = clazz.newInstance();
			t.setArguments(bundle);
			return t;
		} catch (java.lang.InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@CallSuper
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		presenter = createPresenter();
		if (presenter != null) {
			presenter.register(this);
		}
		return inflater.inflate(getLayout(), container, false);
	}

	@CallSuper
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = (FragmentActivity) context;
	}

	@CallSuper
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.rootView = view;
		initHideBoard(view);
		if (getArguments() != null) {
			getBundle(getArguments());
			new BundleUtils().setBundleField(this,getArguments());
		}
		initView(savedInstanceState);
		loadData();
	}

	@Nullable
	protected <V extends View> V findViewById(@IdRes int id) {
		return rootView == null ? null : (V) rootView.findViewById(id);
	}

	private void initHideBoard(View view) {

		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v1, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					if (isTouchView(filterViewByIds(), ev)) return false;
					if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
						return false;
					View v = activity.getCurrentFocus();
					if (isFocusEditText(v, hideSoftByEditViewIds())) {
						if (isTouchView(hideSoftByEditViewIds(), ev))
							return false;
						KeyBoardUtils.hideInputForce(activity);
					}
				}
				return false;
			}
		});
	}

	/**
	 * 传入EditText的Id
	 * 没有传入的EditText不做处理
	 *
	 * @return id 数组
	 */
	protected int[] hideSoftByEditViewIds() {
		return null;
	}

	/**
	 * 隐藏键盘
	 *
	 * @param v   焦点所在View
	 * @param ids 输入框
	 * @return true代表焦点在edit上
	 */
	private boolean isFocusEditText(View v, int... ids) {
		if (v instanceof EditText) {
			EditText tmp_et = (EditText) v;
			for (int id : ids) {
				if (tmp_et.getId() == id) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 传入要过滤的View
	 * 过滤之后点击将不会有隐藏软键盘的操作
	 *
	 * @return id 数组
	 */
	public View[] filterViewByIds() {
		return null;
	}

	//是否触摸在指定view上面,对某个控件过滤
	public boolean isTouchView(int[] ids, MotionEvent ev) {
		int[] location = new int[2];
		for (int id : ids) {
			View view = getView().findViewById(id);
			if (view == null) continue;
			view.getLocationOnScreen(location);
			int x = location[0];
			int y = location[1];
			if (ev.getX() > x && ev.getX() < (x + view.getWidth())
					&& ev.getY() > y && ev.getY() < (y + view.getHeight())) {
				return true;
			}
		}
		return false;
	}

	//是否触摸在指定view上面,对某个控件过滤
	public boolean isTouchView(View[] views, MotionEvent ev) {
		if (views == null || views.length == 0) return false;
		int[] location = new int[2];
		for (View view : views) {
			view.getLocationOnScreen(location);
			int x = location[0];
			int y = location[1];
			if (ev.getX() > x && ev.getX() < (x + view.getWidth())
					&& ev.getY() > y && ev.getY() < (y + view.getHeight())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 当有传值进入fragment时 会调用此方法
	 *
	 * @param bundle
	 */
	protected void getBundle(Bundle bundle) {
	}

	/**
	 * 初始化view
	 *
	 * @param savedInstanceState
	 */
	protected abstract void initView(@Nullable Bundle savedInstanceState);

	/**
	 * 加载数据
	 */
	protected abstract void loadData();

	/**
	 * 创建Presenter
	 *
	 * @return
	 */
	protected abstract T createPresenter();

	@CallSuper
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (presenter != null) {
			presenter.unRegister();
		}
	}

	/**
	 * 返回布局id
	 *
	 * @return 返回布局id
	 * @throws @exception NullPointerException 当没有申明此注解是抛出此异常
	 * @see Layout
	 */
	private @LayoutRes
	int getLayout() {
		if (getClass().isAnnotationPresent(Layout.class)) {
			return getClass().getAnnotation(Layout.class).value();
		} else {
			throw new NullPointerException("You must declare that layout is annotated on the class");
		}
	}


	/**
	 * fragment显示时调用
	 */
	protected void onShow() {
	}

	/**
	 * fragment隐藏时调用
	 */
	protected void onHide() {
	}

	@CallSuper
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden) {
			onHide();
		} else {
			onShow();
		}
	}

	@CallSuper
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Class<? extends BaseFragment> aClass = getClass();

		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			Request request = declaredMethod.getAnnotation(Request.class);
			List<Integer> results = new ArrayList<>();
			if (request != null) {
				for (int i : request.result()) {
					results.add(i);
				}
			}
			boolean isSuccful = request != null && request.request() == requestCode && (request.result()[0] == -1 || (results.contains(resultCode)));
			if (isSuccful) {
				declaredMethod.setAccessible(true);
				try {
					if (data == null) {
						data = new Intent();
					}
					data.putExtra(EXERA_REQUEST, requestCode);
					data.putExtra(EXERA_RESULT, resultCode);
					declaredMethod.invoke(this, data);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return;
			}

		}
	}
}
