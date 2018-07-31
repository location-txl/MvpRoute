package com.location.mvp.mvproutelibrary.Base;

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
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/5/13 0013 20:11
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
	protected T presenter;
	protected final String TAG = getClass().getSimpleName();

	public static final String EXERA_RESULT = "result";

	public static final String EXERA_REQUEST = "request";

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

	protected abstract @LayoutRes
	int getLayout();

	protected abstract void initView();

	protected abstract void loadData();

	protected abstract @NonNull
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

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Request {
		int request();

		int[] result() default {-1};
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
					data.putExtra(EXERA_REQUEST, requestCode);
					data.putExtra(EXERA_RESULT, resultCode);
					declaredMethod.invoke(this, data);
				} catch (IllegalAccessException e) {
					LogUtils.d("error==>" + e.getMessage());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					LogUtils.d("InvocationTargetException==>" + e.getTargetException().getMessage());
					e.printStackTrace();
				}
				return;
			}

		}
	}
}
