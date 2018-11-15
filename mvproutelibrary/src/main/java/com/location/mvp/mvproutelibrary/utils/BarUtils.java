package com.location.mvp.mvproutelibrary.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class BarUtils {

	private static final int DEFAULT_STATUS_BAR_ALPHA = 112;
	private static final String FAKE_STATUS_BAR_VIEW_TAG = "FAKE_STATUS_BAR_VIEW_TAG";
	private static final String FAKE_TOP_VIEW_TAG = "FAKE_TOP_VIEW_TAG";
	private static final int TAG_KEY_HAVE_SET_OFFSET = -123;

	private BarUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
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

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity activity
	 * @param color    状态栏颜色值
	 */
	public static void setStatusBarColor(@NonNull final Activity activity,
										 @ColorInt final int color) {
		setStatusBarColor(activity, color, 0);
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity activity
	 * @param color    状态栏颜色值
	 * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
	 */
	public static void setStatusBarColor(@NonNull final Activity activity,
										 @ColorInt final int color,
										 @IntRange(from = 0, to = 255) final int alpha) {
		Window window = activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getStatusBarColor(color, 0));
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			ViewGroup decorView = (ViewGroup) window.getDecorView();
			View fakeStatusBarView = decorView.findViewWithTag(FAKE_STATUS_BAR_VIEW_TAG);
			if (fakeStatusBarView != null) {
				if (fakeStatusBarView.getVisibility() == View.GONE) {
					fakeStatusBarView.setVisibility(View.VISIBLE);
				}
				fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
			} else {
				decorView.addView(createColorStatusBarView(activity, color, alpha));
			}
			fitWindowAndClipPadding(activity);
		}
	}

	private static int getStatusBarColor(@ColorInt final int color, final int alpha) {
		if (alpha == 0) {
			return color;
		}
		float a = 1 - alpha / 255f;
		int red = (color >> 16) & 0xff;
		int green = (color >> 8) & 0xff;
		int blue = color & 0xff;
		red = (int) (red * a + 0.5);
		green = (int) (green * a + 0.5);
		blue = (int) (blue * a + 0.5);
		return Color.argb(255, red, green, blue);
	}

	private static View createColorStatusBarView(@NonNull final Activity activity,
												 @ColorInt final int color,
												 final int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
		statusBarView.setTag(FAKE_STATUS_BAR_VIEW_TAG);
		return statusBarView;
	}

	/**
	 * 为背景图设置状态栏透明度
	 * <p>适用于图片作为背景的界面,此时需要图片填充到状态栏</p>
	 *
	 * @param activity activity
	 */
	public static void setStatusBar4Bg(@NonNull final Activity activity) {
		setStatusBar4Bg(activity, DEFAULT_STATUS_BAR_ALPHA);
	}

	/**
	 * 为背景图设置状态栏透明度
	 * <p>适用于图片作为背景的界面,此时需要图片填充到状态栏</p>
	 *
	 * @param activity activity
	 * @param alpha    状态栏透明度
	 */
	public static void setStatusBar4Bg(@NonNull final Activity activity,
									   @IntRange(from = 0, to = 255) final int alpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		transparentStatusBar(activity);
		addTopView(activity, alpha);
		fitWindowAndClipPadding(activity);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private static void transparentStatusBar(@NonNull final Activity activity) {
		Window window = activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			window.getDecorView().setSystemUiVisibility(option);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else {
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

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

	private static void addTopView(@NonNull final Activity activity,
								   @IntRange(from = 0, to = 255) final int alpha) {
		ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
		View fakeTranslucentView = contentView.findViewWithTag(FAKE_TOP_VIEW_TAG);
		if (fakeTranslucentView != null) {
			if (fakeTranslucentView.getVisibility() == View.GONE) {
				fakeTranslucentView.setVisibility(View.VISIBLE);
			}
			fakeTranslucentView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
		} else {
			contentView.addView(createAlphaStatusBarView(activity, alpha));
		}
	}

	private static View createAlphaStatusBarView(@NonNull final Activity activity,
												 final int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
		statusBarView.setTag(FAKE_TOP_VIEW_TAG);
		return statusBarView;
	}

	/**
	 * 为头部ImageView设置状态栏透明度
	 * <p>如果</p>
	 *
	 * @param activity       activity
	 * @param needOffsetView 需要向下偏移的 View
	 */
	public static void setStatusBar4ImageView(@NonNull final Activity activity,
											  @Nullable final View needOffsetView) {
		setStatusBar4ImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
	}

	/**
	 * 为头部ImageView设置状态栏透明度
	 *
	 * @param activity       activity
	 * @param alpha          状态栏透明度
	 * @param needOffsetView 需要向下偏移的 View
	 */
	public static void setStatusBar4ImageView(@NonNull final Activity activity,
											  @IntRange(from = 0, to = 255) final int alpha,
											  @Nullable final View needOffsetView) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		transparentStatusBar(activity);
		addTopView(activity, alpha);
		ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
		if (needOffsetView != null) {
			Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
			if (haveSetOffset != null && (Boolean) haveSetOffset) {
				return;
			}
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView
					.getLayoutParams();
			layoutParams.setMargins(layoutParams.leftMargin,
					layoutParams.topMargin + getStatusBarHeight(activity),
					layoutParams.rightMargin, layoutParams.bottomMargin);
			needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
		}
	}

	/**
	 * 为fragment头部是ImageView设置状态栏透明度
	 *
	 * @param activity       activity
	 * @param needOffsetView 需要向下偏移的 View
	 */
	public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
														@Nullable final View needOffsetView) {
		setStatusBar4ImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
	}

	/**
	 * 为fragment头部是ImageView设置状态栏透明度
	 *
	 * @param activity       activity
	 * @param alpha          状态栏透明度
	 * @param needOffsetView 需要向下偏移的 View
	 */
	public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
														@IntRange(from = 0, to = 255) final int alpha,
														@Nullable final View needOffsetView) {
		setStatusBar4ImageView(activity, alpha, needOffsetView);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
			View fakeStatusBarView = decorView.findViewWithTag(FAKE_STATUS_BAR_VIEW_TAG);
			if (fakeStatusBarView != null) {
				decorView.removeView(fakeStatusBarView);
				ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))
						.getChildAt(0);
				rootView.setPadding(rootView.getPaddingLeft(), 0, rootView.getPaddingRight(),
						rootView.getPaddingBottom());
			}
		}
	}

	/**
	 * 为滑动返回界面设置状态栏颜色
	 *
	 * @param activity 需要设置的activity
	 * @param color    状态栏颜色值
	 */
	public static void setColorForSwipeBack(@NonNull final Activity activity, final int color) {
		setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
	}

	/**
	 * 为滑动返回界面设置状态栏颜色
	 *
	 * @param activity 需要设置的activity
	 * @param color    状态栏颜色值
	 * @param alpha    状态栏透明度
	 */
	public static void setColorForSwipeBack(@NonNull final Activity activity,
											@ColorInt final int color,
											@IntRange(from = 0, to = 255) final int alpha) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
			View rootView = contentView.getChildAt(0);
			int statusBarHeight = getStatusBarHeight(activity);
			if (rootView != null && rootView instanceof CoordinatorLayout) {
				final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
					coordinatorLayout.setFitsSystemWindows(false);
					contentView.setBackgroundColor(getStatusBarColor(color, alpha));
					boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
					if (isNeedRequestLayout) {
						contentView.setPadding(contentView.getPaddingLeft(),
								statusBarHeight,
								contentView.getPaddingRight(),
								contentView.getPaddingBottom());
						coordinatorLayout.post(new Runnable() {
							@Override
							public void run() {
								coordinatorLayout.requestLayout();
							}
						});
					}
				} else {
					coordinatorLayout.setStatusBarBackgroundColor(getStatusBarColor(color, alpha));
				}
			} else {
				contentView.setPadding(contentView.getPaddingLeft(),
						statusBarHeight,
						contentView.getPaddingRight(),
						contentView.getPaddingBottom());
				contentView.setBackgroundColor(getStatusBarColor(color, alpha));
			}
			transparentStatusBar(activity);
		}
	}

	/**
	 * 针对根布局是 CoordinatorLayout, 使状态栏半透明
	 * <p>
	 * 适用于图片作为背景的界面,此时需要图片填充到状态栏
	 *
	 * @param activity 需要设置的activity
	 * @param alpha    状态栏透明度
	 */
	public static void setTranslucentForCoordinatorLayout(@NonNull final Activity activity,
														  @IntRange(from = 0, to = 255) final int alpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		transparentStatusBar(activity);
		addTopView(activity, alpha);
	}


	/**
	 * 为DrawerLayout 布局设置状态栏变色
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 * @param color        状态栏颜色值
	 */
	public static void setColorForDrawerLayout(@NonNull final Activity activity,
											   @NonNull final DrawerLayout drawerLayout,
											   @ColorInt final int color) {
		setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
	}

	/**
	 * 为DrawerLayout 布局设置状态栏颜色,纯色
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 * @param color        状态栏颜色值
	 */
	public static void setColorNoTranslucentForDrawerLayout(@NonNull final Activity activity,
															@NonNull final DrawerLayout drawerLayout,
															@ColorInt final int color) {
		setColorForDrawerLayout(activity, drawerLayout, color, 0);
	}

	/**
	 * 为DrawerLayout 布局设置状态栏变色
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 * @param color        状态栏颜色值
	 * @param alpha        状态栏透明度
	 */
	public static void setColorForDrawerLayout(@NonNull final Activity activity,
											   @NonNull final DrawerLayout drawerLayout,
											   final @ColorInt int color,
											   @IntRange(from = 0, to = 255) final int alpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		// 生成一个状态栏大小的矩形
		// 添加 statusBarView 到布局中
		ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
		View fakeStatusBarView = contentLayout.findViewWithTag(FAKE_STATUS_BAR_VIEW_TAG);
		if (fakeStatusBarView != null) {
			if (fakeStatusBarView.getVisibility() == View.GONE) {
				fakeStatusBarView.setVisibility(View.VISIBLE);
			}
			fakeStatusBarView.setBackgroundColor(color);
		} else {
			contentLayout.addView(createColorStatusBarView(activity, color, 0), 0);
		}
		// 内容布局不是 LinearLayout 时,设置padding top
		if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
			contentLayout.getChildAt(1)
					.setPadding(contentLayout.getPaddingLeft(),
							getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
							contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
		}
		// 设置属性
		setDrawerLayoutProperty(drawerLayout, contentLayout);
		addTopView(activity, alpha);
	}

	/**
	 * 设置 DrawerLayout 属性
	 *
	 * @param drawerLayout              DrawerLayout
	 * @param drawerLayoutContentLayout DrawerLayout 的内容布局
	 */
	private static void setDrawerLayoutProperty(@NonNull final DrawerLayout drawerLayout,
												@NonNull final ViewGroup drawerLayoutContentLayout) {
		ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
		drawerLayout.setFitsSystemWindows(false);
		drawerLayoutContentLayout.setFitsSystemWindows(false);
		drawerLayoutContentLayout.setClipToPadding(true);
		drawer.setFitsSystemWindows(false);
	}

	/**
	 * 为 DrawerLayout 布局设置状态栏透明
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 */
	public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
													 @NonNull final DrawerLayout drawerLayout) {
		setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
	}

	/**
	 * 为 DrawerLayout 布局设置状态栏透明
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 */
	public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
													 @NonNull final DrawerLayout drawerLayout,
													 @IntRange(from = 0, to = 255) final int alpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		setTransparentForDrawerLayout(activity, drawerLayout);
		addTopView(activity, alpha);
	}

	/**
	 * 为 DrawerLayout 布局设置状态栏透明
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 */
	public static void setTransparentForDrawerLayout(@NonNull final Activity activity,
													 @NonNull final DrawerLayout drawerLayout) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
		// 内容布局不是 LinearLayout 时,设置padding top
		if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
			contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
		}

		// 设置属性
		setDrawerLayoutProperty(drawerLayout, contentLayout);
	}

	/**
	 * 隐藏伪状态栏 View
	 *
	 * @param activity 调用的 Activity
	 */
	public static void hideFakeStatusBarView(@NonNull final Activity activity) {
		ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
		View fakeStatusBarView = decorView.findViewWithTag(FAKE_STATUS_BAR_VIEW_TAG);
		if (fakeStatusBarView != null) {
			fakeStatusBarView.setVisibility(View.GONE);
		}
		View fakeTranslucentView = decorView.findViewWithTag(FAKE_TOP_VIEW_TAG);
		if (fakeTranslucentView != null) {
			fakeTranslucentView.setVisibility(View.GONE);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////

    /*--------------------------------old--------------------------------*/

	/**
	 * 设置透明状态栏（api大于19方可使用）
	 * <p>可在Activity的onCreat()中调用</p>
	 * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下</p>
	 * <p>android:clipToPadding="true"</p>
	 * <p>android:fitsSystemWindows="true"</p>
	 *
	 * @param activity      activity
	 * @param isTranslucent 是否半透明
	 */
	public static void setTransparentStatusBar(@NonNull final Activity activity, boolean isTranslucent) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (isTranslucent) {
				//设置半透明
				activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			}
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	public static void setTransparentStatusBar(Activity activity){
		setTransparentStatusBar(activity,false);
	}

	/**
	 * 隐藏状态栏
	 * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
	 * <p>此方法Activity可以继承AppCompatActivity</p>
	 * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
	 * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
	 * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
	 *
	 * @param activity activity
	 */
	public static void hideStatusBar(@NonNull final Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
	 * 获取ActionBar高度
	 *
	 * @param activity activity
	 * @return ActionBar高度
	 */
	public static int getActionBarHeight(@NonNull final Activity activity) {
		TypedValue tv = new TypedValue();
		if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			return TypedValue
					.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
		}
		return 0;
	}

	/**
	 * 显示通知栏
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
	 *
	 * @param context        上下文
	 * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
	 */
	public static void showNotificationBar(@NonNull final Context context,
										   final boolean isSettingPanel) {
		String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
				: (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
		invokePanels(context, methodName);
	}

	/**
	 * 隐藏通知栏
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
	 *
	 * @param context 上下文
	 */
	public static void hideNotificationBar(@NonNull final Context context) {
		String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
		invokePanels(context, methodName);
	}

	/**
	 * 反射唤醒通知栏
	 *
	 * @param context    上下文
	 * @param methodName 方法名
	 */
	private static void invokePanels(@NonNull final Context context, final String methodName) {
		try {
			@SuppressLint("WrongConstant") Object service = context.getSystemService("statusbar");
			Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
			Method expand = statusBarManager.getMethod(methodName);
			expand.invoke(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// navigation bar
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 获取导航栏高度
	 *
	 * @return 导航栏高度，0代表不存在
	 */
	public int getNavigationBarHeight(Context context) {
		boolean hasMenuKey = ViewConfiguration.get(context)
				.hasPermanentMenuKey();
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		if (!hasMenuKey && !hasBackKey) {
			Resources res = context.getResources();
			int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
			return res.getDimensionPixelSize(resourceId);
		} else {
			return 0;
		}
	}

	private static final int MIUI = 1;
	private static final int FLYME = 2;
	private static final int ANDROID_M = 3;

	/**
	 * 状态栏亮色模式，设置状态栏黑色文字、图标，
	 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 *
	 * @return 1:MIUUI 2:Flyme 3:android6.0
	 */
	public static int setStatusBarLightMode(Activity activity) {
		int result = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (MIUISetStatusBarLightMode(activity, true)) {
				result = MIUI;
			} else if (FLYMESetStatusBarLightMode(activity.getWindow(), true)) {
				result = FLYME;
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				result = ANDROID_M;
			}
		}
		return result;
	}

	public static int setStatusBarWhiteMode(Activity activity) {
		int result = 0;
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			if (MIUISetStatusBarLightMode(activity, true)) {
//				result = MIUI;
//			} else if (FLYMESetStatusBarLightMode(activity.getWindow(), true)) {
//				result = FLYME;
//			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
//				result = ANDROID_M;
//			}
//		}
		return result;
	}

	/**
	 * 已知系统类型时，设置状态栏黑色文字、图标。
	 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 *
	 * @param type 1:MIUUI 2:Flyme 3:android6.0
	 */
	public static void setStatusBarLightMode(Activity activity, int type) {
		if (type == MIUI) {
			MIUISetStatusBarLightMode(activity, true);
		} else if (type == FLYME) {
			FLYMESetStatusBarLightMode(activity.getWindow(), true);
		} else if (type == ANDROID_M) {
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
	}

	/**
	 * 设置状态栏图标为深色和魅族特定的文字风格
	 * 可以用来判断是否为Flyme用户
	 *
	 * @param window 需要设置的窗口
	 * @param dark   是否把状态栏文字及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	private static boolean FLYMESetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (window != null) {
			try {
				WindowManager.LayoutParams lp = window.getAttributes();
				Field darkFlag = WindowManager.LayoutParams.class
						.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
				Field meizuFlags = WindowManager.LayoutParams.class
						.getDeclaredField("meizuFlags");
				darkFlag.setAccessible(true);
				meizuFlags.setAccessible(true);
				int bit = darkFlag.getInt(null);
				int value = meizuFlags.getInt(lp);
				if (dark) {
					value |= bit;
				} else {
					value &= ~bit;
				}
				meizuFlags.setInt(lp, value);
				window.setAttributes(lp);
				result = true;
			} catch (Exception e) {

			}
		}
		return result;
	}

	/**
	 * 需要MIUIV6以上
	 *
	 * @param dark 是否把状态栏文字及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	private static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
		boolean result = false;
		Window window = activity.getWindow();
		if (window != null) {
			Class clazz = window.getClass();
			try {
				int darkModeFlag = 0;
				Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
				Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
				darkModeFlag = field.getInt(layoutParams);
				Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
				if (dark) {
					extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
				} else {
					extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
				}
				result = true;

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					//开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
					if (dark) {
						activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					} else {
						activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
