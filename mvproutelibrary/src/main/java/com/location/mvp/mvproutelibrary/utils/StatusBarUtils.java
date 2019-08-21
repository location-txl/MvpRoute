package com.location.mvp.mvproutelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * create：2018/11/15 22:21
 */

public class StatusBarUtils {
	private static final String FAKE_STATUS_BAR_VIEW_TAG = "FAKE_STATUS_BAR_VIEW_TAG";
	private static final String FAKE_TOP_VIEW_TAG = "FAKE_TOP_VIEW_TAG";

	/**
	 * 设置让内容显示在状态栏之下
	 *
	 * @param activity
	 */
	public static void fitWindowAndClipPadding(@NonNull final Activity activity) {
		ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
		for (int i = 0, count = parent.getChildCount(); i < count; i++) {
			View childView = parent.getChildAt(i);
			if (childView instanceof ViewGroup) {
				childView.setFitsSystemWindows(true);
				((ViewGroup) childView).setClipToPadding(true);
			}
		}
	}


	/**
	 * 隐藏状态栏
	 * <p>也就是设置全屏</p>
	 * <p>此方法Activity可以继承AppCompatActivity</p>
	 * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
	 * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
	 * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
	 *
	 * @param activity activity
	 */
	public static void hideStatusBar(@NonNull final Activity activity) {
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


	/**
	 * 显示状态栏
	 */
	public static void showStatusBar(@NonNull final Activity activity) {
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 判断状态栏是否存在
	 *
	 * @param activity activity
	 * @return {@code true}: 存在<br>{@code false}: 不存在
	 */
	public static boolean isStatusBarExists(@NonNull final Activity activity) {
		WindowManager.LayoutParams params = activity.getWindow().getAttributes();
		return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
				!= WindowManager.LayoutParams.FLAG_FULLSCREEN;
	}


	/**
	 * 设置透明状态栏（api大于19方可使用）
	 * <p>可在Activity的onCreat()中调用</p>
	 * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下</p>
	 * <p>android:clipToPadding="true"</p>
	 * <p>android:fitsSystemWindows="true"</p>
	 *
	 * @param activity      activity
	 */
	public static void setTransparentStatusBar(@NonNull final Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				//设置半透明
				activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity activity
	 * @param color    状态栏颜色值
	 */
	public static void setStatusBarColor(@NonNull final Activity activity,
										 @ColorInt final int color) {
		Window window = activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(color);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			ViewGroup decorView = (ViewGroup) window.getDecorView();
			View fakeStatusBarView = decorView.findViewWithTag(FAKE_STATUS_BAR_VIEW_TAG);
			if (fakeStatusBarView != null) {
				if (fakeStatusBarView.getVisibility() == View.GONE) {
					fakeStatusBarView.setVisibility(View.VISIBLE);
				}
				fakeStatusBarView.setBackgroundColor(color);
			} else {
				decorView.addView(createColorStatusBarView(activity, color));
			}
			fitWindowAndClipPadding(activity);
		}
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context context
	 * @return 状态栏高度
	 */
	public static int getStatusBarHeight(@NonNull final Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	private static View createColorStatusBarView(@NonNull final Activity activity,
												 @ColorInt final int color
	) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(color);
		statusBarView.setTag(FAKE_STATUS_BAR_VIEW_TAG);
		return statusBarView;
	}
}
