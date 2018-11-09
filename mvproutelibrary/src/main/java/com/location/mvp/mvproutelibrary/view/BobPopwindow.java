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
package com.location.mvp.mvproutelibrary.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


public class BobPopwindow extends PopupWindow {
	public BobPopwindow(Context context) {
		super(context);
	}

	public BobPopwindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BobPopwindow(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BobPopwindow() {
		super();
	}

	public BobPopwindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}


	public void setOnClick(@IdRes int viewid, View.OnClickListener onClickListener) {
		getContentView().findViewById(viewid).setOnClickListener(onClickListener);
	}

	public void setOnClick(@IdRes final int viewid, final OnClickListener onClickListener) {
		getContentView().findViewById(viewid).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onClickListener != null) {
					onClickListener.onClick(BobPopwindow.this, v);
				}
			}
		});
	}

	public void setText(@IdRes int viewid, String content) {
		View contentView = getContentView();
		View viewById = contentView.findViewById(viewid);
		if (viewById instanceof TextView) {
			((TextView) viewById).setText(content);
		}

	}

	public void setVisible(@IdRes int viewid) {
		View view = getContentView().findViewById(viewid);
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		}
	}

	public void setGone(@IdRes int viewid) {
		View view = getContentView().findViewById(viewid);
		if (view != null) {
			view.setVisibility(View.GONE);
		}
	}
	public void showAtLocation(View parent, int gravity){
		showAtLocation(parent,gravity,0,0);
	}


	public BobPopwindow(View contentView) {
		super(contentView);
	}

	public BobPopwindow(int width, int height) {
		super(width, height);
	}

	public BobPopwindow(View contentView, int width, int height) {
		super(contentView, width, height);
	}

	public BobPopwindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
	}

	private boolean isdarken;
	private Activity activity;

	private float alpha = 0.8f;


	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}


	public void setActivity(Activity activity) {
		this.activity = activity;
	}


	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		darken();
	}

	public <T extends View> T getChildView(@IdRes int viewid) {
		return getContentView().findViewById(viewid);
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
		darken();
		super.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	private void darken() {
		if (activity != null) {
			WindowManager.LayoutParams params = activity.getWindow().getAttributes();
			params.alpha = alpha;
			isdarken = true;
			activity.getWindow().setAttributes(params);
			/**
			 * 设置点击外部可以消失
			 */
			setTouchInterceptor(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {//判断是不是点击了外部

					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						return true;
					}
					return false;
				}
			});
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (isdarken) {
			WindowManager.LayoutParams params = activity.getWindow().getAttributes();
			params.alpha = 1f;
			activity.getWindow().setAttributes(params);
		}
	}


	public static class Builder {

		private final BobPopwindow popwindow;

		public Builder() {
			popwindow = new BobPopwindow();
		}

		public Builder(View view) {
			popwindow = new BobPopwindow(view);
		}

		public Builder(View view, int width, int height) {
			popwindow = new BobPopwindow(view, width, height);
		}

		public Builder setView(View view) {
			popwindow.setContentView(view);
			return this;
		}

		public Builder setParams(ViewGroup.LayoutParams params) {
			popwindow.setWidth(params.width);
			popwindow.setHeight(params.height);
			return this;
		}

		public Builder setBackground(Drawable drawable) {
			popwindow.setBackgroundDrawable(drawable);
			return this;
		}

		public Builder setFocusable(boolean focusable) {
			popwindow.setFocusable(focusable);
			return this;
		}

		public  Builder getChildView(@IdRes int viewid, BobDataView dataView) {
			View childView = popwindow.getChildView(viewid);
			dataView.setView(childView);
			return this;
		}

		public Builder setOutsideTouchable(boolean outsideTouchable) {
			popwindow.setOutsideTouchable(outsideTouchable);
			return this;
		}

		public Builder setTouchable(boolean touchable) {
			popwindow.setTouchable(touchable);
			return this;
		}

		public Builder setGone(@IdRes int viewid) {
			popwindow.setGone(viewid);
			return this;
		}

		public Builder setVisible(@IdRes int viewid) {
			popwindow.setVisible(viewid);
			return this;
		}

		public Builder setDarken(Activity activity) {
			popwindow.setActivity(activity);
			if (popwindow.getBackground() == null) {
				popwindow.setBackgroundDrawable(new ColorDrawable());
			}
			popwindow.setOutsideTouchable(true);
			popwindow.setFocusable(true);
			popwindow.setTouchable(true);
			return this;
		}

		public Builder setAlpha(@FloatRange(from = 0.1f, to = 1.0f) float alpha) {
			popwindow.setAlpha(alpha);
			return this;
		}

		public Builder setAnim(int anim) {
			popwindow.setAnimationStyle(anim);
			return this;
		}


		public Builder setViewClick(@IdRes int viewid, View.OnClickListener onClickListener) {
			popwindow.setOnClick(viewid, onClickListener);
			return this;
		}

		public Builder setViewClick(@IdRes int viewid, OnClickListener onClickListener) {
			popwindow.setOnClick(viewid, onClickListener);
			return this;
		}

		public Builder setText(@IdRes int id, String content) {
			popwindow.setText(id, content);
			return this;
		}

		public BobPopwindow create() {
			return popwindow;
		}

	}


	public interface OnClickListener {
		void onClick(BobPopwindow bobPopwindow, View veiw);
	}

	public static class BobDataView {
		private View view;

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}
	}
}
