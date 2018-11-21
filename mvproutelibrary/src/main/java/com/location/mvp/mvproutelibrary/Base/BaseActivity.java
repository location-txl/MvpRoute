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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.location.mvp.mvproutelibrary.utils.KeyBoardUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



/**
 * 基类BaseActivity
 * @param <T>
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

	protected T presenter;
	protected final String TAG = getClass().getSimpleName();



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		presenter = createPresenter();
		if (presenter != null) {
			presenter.regist(this);
		}
		initView();
		loadData();
	}

	protected KeyBordListener keyBordListener;

	/**
	 * 返回布局id
	 * @return
	 */
	protected abstract @LayoutRes
	int getLayout();

	/**
	 * 初始化view
	 */
	protected abstract void initView();

	/**
	 * 加载数据
	 */
	protected abstract void loadData();

	/**
	 * 创建Presenter
	 * @return
	 */
	protected abstract
	T createPresenter();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (presenter != null) {
			presenter.unRegist();
		}
	}

	protected void startActivity(Class activity) {
		startActivity(new Intent(this, activity));
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
			if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
				return super.dispatchTouchEvent(ev);
			View v = getCurrentFocus();
			if (isFocusEditText(v, hideSoftByEditViewIds())) {
				if (isTouchView(hideSoftByEditViewIds(), ev))
					return super.dispatchTouchEvent(ev);
				//隐藏键盘
				KeyBoardUtils.hideInputForce(this);
				if (keyBordListener != null) {
					keyBordListener.onHideKeyBord();
				}
			}
		}


		return super.dispatchTouchEvent(ev);
	}

	//是否触摸在指定view上面,对某个控件过滤
	public boolean isTouchView(int[] ids, MotionEvent ev) {
		int[] location = new int[2];
		for (int id : ids) {
			View view = findViewById(id);
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


	public interface KeyBordListener {
		void onHideKeyBord();
	}



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
}
