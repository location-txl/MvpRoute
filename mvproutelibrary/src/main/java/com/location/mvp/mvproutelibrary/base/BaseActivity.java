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
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.location.mvp.mvproutelibrary.utils.AppManager;
import com.location.mvp.mvproutelibrary.utils.KeyBoardUtils;
import com.location.mvp.mvproutelibrary.utils.StatusBarUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



/**
 * 基类BaseActivity
 * 设置{@link Layout} 注解来绑定布局
 * 设置{@link FullScreen}    注解来使Activity全屏
 * 设置{@link StatusBar}     注解设置沉浸式状态栏
 * 设置{@link InjectBundle}  注解来注入字段  无需手写bundle get方法
 * 设置{@link Request}       注解来设置onActivityResult回调方法  无需手动重写{@link #onActivityResult(int, int, Intent)}
 *
 * @param <T>
 * @author location
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
	protected T presenter;

	protected final String TAG = getClass().getSimpleName();

	protected BaseActivity activity;

	@CallSuper
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置状态栏
		setStatusBar();
		//设置布局
		setContentView();
		//透明状态栏所用
		initPaddingTop();
		activity = this;
		presenter = createPresenter();
		if (presenter != null) {
			presenter.register(this);
		}
		AppManager.getAppManager().addActivity(this);
		Bundle bundle = getIntent().getExtras();
		// 设置bundle传值注入
		if (bundle != null) {
			new BundleUtils().setBundleField(this, bundle);
		}
		initView(savedInstanceState);
		loadData();
	}

	/**
	 * 设置 在沉浸状态栏下的  内容是否在状态栏之下
	 */
	private void initPaddingTop() {
		Class<? extends BaseActivity> baseClass = getClass();
		if (baseClass.isAnnotationPresent(StatusBar.class)) {
			StatusBar statusBar = baseClass.getAnnotation(StatusBar.class);
			// 只有是沉浸式 状态栏才有效
			if (statusBar.tranStatus() && statusBar.paddingTop()) {
				StatusBarUtils.fitWindowAndClipPadding(this);
			}
		} else if (RouteManager.TRANSPARENR_STATUS && RouteManager.STATUS_PADDING_TOP) {
			StatusBarUtils.fitWindowAndClipPadding(this);
		}
	}

	/**
	 * 获取注解 设置 透明状态栏 是否全屏
	 *
	 * @see StatusBar 状态栏注解
	 * @see FullScreen 全屏注解
	 */
	private void setStatusBar() {
		Class<? extends BaseActivity> baseClass = getClass();
		//判断有没有注解
		boolean haveStatusBar = baseClass.isAnnotationPresent(StatusBar.class);
		if (haveStatusBar) {
			StatusBar statusBar = baseClass.getAnnotation(StatusBar.class);
			int c = statusBar.color();
			if (statusBar.tranStatus()) {
				//设置透明状态栏
				StatusBarUtils.setTransparentStatusBar(this);
				c = Color.TRANSPARENT;
			}
			setStatusBarColorForAnnotation(c);
		} else if (RouteManager.TRANSPARENR_STATUS) {
			StatusBarUtils.setTransparentStatusBar(this);
			//解决在某些机型上 透明状态栏后有半透明的黑色背景
			StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT);
		} else {
//			setStatusBarColorFromManager();
		}


		//是否是全屏
		if (baseClass.isAnnotationPresent(FullScreen.class)) {
			StatusBarUtils.hideStatusBar(this);
		}
	}

	/**
	 * @param c 注解设置的颜色
	 *          如果注解没有设置颜色  则会获取{@link RouteManager#STATUS_COLOR} 颜色
	 *          还没有获取到  则获取app的主题颜色{@link #getDefaultColor()}
	 */
	private void setStatusBarColorForAnnotation(int c) {
		if (RouteManager.STATUS_COLOR != -1 && c == -1) {
			c = RouteManager.STATUS_COLOR;
		}
		if (c == -1) {
			c = getDefaultColor();
		}
		StatusBarUtils.setStatusBarColor(this, c);
	}

	private void setStatusBarColorFromManager() {
		int color = RouteManager.STATUS_COLOR;
		if (color != -1) {
			StatusBarUtils.setStatusBarColor(this, color);
		}
	}

	/**
	 * 获取布局id注解
	 *
	 * @throws NullPointerException 当没有申明此注解是抛出此异常
	 * @see Layout
	 */
	private void setContentView() {
		Class<? extends BaseActivity> aClass = getClass();
		boolean annotationPresent = aClass.isAnnotationPresent(Layout.class);
		if (annotationPresent) {
			setContentView(aClass.getAnnotation(Layout.class).value());
		} else {
			throw new NullPointerException("You must declare that layout is annotated on the class");
		}
	}

	protected KeyBordListener keyBordListener;


	/**
	 * 初始化view
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
	protected void onDestroy() {
		super.onDestroy();
		if (presenter != null) {
			presenter.unRegister();
		}
		AppManager.getAppManager().finishActivity(this);
	}

	protected void startActivity(Class activity) {
		startActivity(new Intent(this, activity));
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (isTouchView(filterViewByIds(), ev)) {
				return super.dispatchTouchEvent(ev);
			}
			if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0) {

				return super.dispatchTouchEvent(ev);
			}
			View v = getCurrentFocus();
			if (isFocusEditText(v, hideSoftByEditViewIds())) {
				if (isTouchView(hideSoftByEditViewIds(), ev)) {

					return super.dispatchTouchEvent(ev);
				}
				//隐藏键盘
				KeyBoardUtils.hideInputForce(this);
				if (keyBordListener != null) {
					keyBordListener.onHideKeyBord();
				}
			}
		}


		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 是否触摸在指定view上面,对某个控件过滤
	 *
	 * @param ids
	 * @param ev
	 * @return
	 */
	public boolean isTouchView(int[] ids, MotionEvent ev) {
		int[] location = new int[2];
		for (int id : ids) {
			View view = findViewById(id);
			if (view == null) {
				continue;
			}
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
	 * 传入EditText的Id
	 * 没有传入的EditText不做处理
	 *
	 * @return id 数组
	 */
	public int[] hideSoftByEditViewIds() {
		return null;
	}

	/**
	 * 隐藏键盘
	 *
	 * @param v   焦点所在View
	 * @param ids 输入框
	 * @return true代表焦点在edit上
	 */
	public boolean isFocusEditText(View v, int... ids) {
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

	/**
	 * 是否触摸在指定view上面,对某个控件过滤
	 *
	 * @param views
	 * @param ev
	 * @return
	 */
	public boolean isTouchView(View[] views, MotionEvent ev) {
		if (views == null || views.length == 0) {
			return false;
		}
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


	public interface KeyBordListener {
		/**
		 * 键盘隐藏监听
		 */
		void onHideKeyBord();
	}


	@CallSuper
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Class<? extends BaseActivity> aClass = getClass();
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
					data.putExtra(Request.EXERA_REQUEST, requestCode);
					data.putExtra(Request.EXERA_RESULT, resultCode);
					declaredMethod.invoke(this, data);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return;
			}

		}
	}

	/**
	 * 获取默认的状态栏颜色
	 *
	 * @return
	 */
	private int getDefaultColor() {
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
		return typedValue.data;
	}
}
