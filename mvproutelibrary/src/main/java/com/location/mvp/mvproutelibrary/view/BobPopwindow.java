package com.location.mvp.mvproutelibrary.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ThemedSpinnerAdapter;

import com.location.mvp.mvproutelibrary.http.RetrofitClient;

/**
 * 项目:趣租部落
 * 类描述:  加阴影的工具类
 * 创建人: txl
 * 创建时间: 2017/8/15 18:25
 * 修改人:
 * 修改内容:
 * 修改时间:
 */
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


		public Builder setOutsideTouchable(boolean outsideTouchable) {
			popwindow.setOutsideTouchable(outsideTouchable);
			return this;
		}

		public Builder setTouchable(boolean touchable) {
			popwindow.setTouchable(touchable);
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

		public BobPopwindow create() {
			return popwindow;
		}

	}

}
