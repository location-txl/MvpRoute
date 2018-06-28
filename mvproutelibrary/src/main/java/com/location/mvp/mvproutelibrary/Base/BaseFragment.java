package com.location.mvp.mvproutelibrary.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.location.mvp.mvproutelibrary.utils.KeyBoardUtils;

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
	protected BaseActivity activity;


	public static <T extends BaseFragment> T newInstance(Class<? extends T> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T extends BaseFragment> T newInstance(Class<? extends T> clazz, Bundle bundle) {
		try {
			T t = clazz.newInstance();
			t.setArguments(bundle);
			return t;
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

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
		initHideBoard(view);
		if (getArguments() != null) {
			getBundle(getArguments());
		}
		initView(view);
		loadData();
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
						//隐藏键盘
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
