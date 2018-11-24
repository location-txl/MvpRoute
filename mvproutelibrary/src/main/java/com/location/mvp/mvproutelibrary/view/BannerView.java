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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.location.mvp.mvproutelibrary.R;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 自定义轮播图 基于RxJava
 * 默认轮播图标志位在中间  查看此方法 @see {@link #BannerView(Context, AttributeSet, int)}
 * 需要设定其他位置的需要自己编写对外方法提供位置
 * 下方table是使用的radiobutton
 *
 * 7/31 添加  banner标题方法  注释setTitles()方法
 * 维护2x
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
	private final String TAG = getClass().getSimpleName();
	private final int DEFAULT_INTERVATIME = 3;

	private final boolean DEFAULAT_PALYER = false;

	private final int DEFAULT_MARGIN = 10;

	private final int DEFAULT_BOOTTOM_MARGIN = 20;

	private final int DEFAULT_VIEWPAGER_SPEED = 1000;
	private static final int DEFAULT_NUMBER = 10000;

	private final int DEFAULT_TAB_WIDTH = 20;
	private final int DEFAULT_TAB_HEIGHT = 20;

	/**
	 * 是否自动轮播
	 */
	private boolean isPlayStart;
	//间隔时间   单位秒 默认时间3秒
	private int intervalTime = DEFAULT_INTERVATIME;

	/**
	 * 背景选择器
	 */
	private @DrawableRes
	int selectTab;


	private int tabWidth;
	private int tabHeight;
	private ViewPager viewpager;
	private RadioGroup radioGroup;
	private Context context;

	private LinkedList<View> imgs;

	private LinkedList<String> titles;

	private BannerAdapter bannerAdapter;
	private Disposable disposable;
	private int tabMargin;
	private int tabBottomMargin;
	private int speed;

	/**
	 * 绑定数据源
	 */
	private LoadImageListener loadImageListener;
	private TextView textView;

	public void setLoadImageListener(LoadImageListener loadImageListener) {
		this.loadImageListener = loadImageListener;
	}

	private BannerOnItemClickListener onItemClickListener;


	public void setOnItemClickListener(BannerOnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public BannerView(Context context) {
		this(context, null);
	}


	public BannerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context, attrs);
	}

	/**
	 * 是否自动轮播
	 *
	 * @param isStart
	 */
	public void setPlayStart(boolean isStart) {
		this.isPlayStart = isStart;
	}


	/**
	 * 轮播图切换的间隔时间  单位是秒
	 *
	 * @param intervalTime
	 */
	public void setIntervalTime(@IntRange(from = 0) int intervalTime) {
		this.intervalTime = intervalTime;
	}

	/**
	 * 初始化BannerView  加入ViewPager 加入RadioGroup
	 *
	 * @param context
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);

		try {
			isPlayStart = typedArray.getBoolean(R.styleable.BannerView_start_plary, DEFAULAT_PALYER);
			selectTab = typedArray.getResourceId(R.styleable.BannerView_tab_select, 0);
			tabMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_tab_margin, DEFAULT_MARGIN);
			tabBottomMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_tab_bottom_margin, DEFAULT_BOOTTOM_MARGIN);
			speed = typedArray.getInt(R.styleable.BannerView_viewpager_speed, DEFAULT_VIEWPAGER_SPEED);
			intervalTime = typedArray.getInt(R.styleable.BannerView_intervalTime, DEFAULT_INTERVATIME);
			tabWidth = typedArray.getDimensionPixelSize(R.styleable.BannerView_tab_width, DEFAULT_TAB_WIDTH);
			tabHeight = typedArray.getDimensionPixelSize(R.styleable.BannerView_tab_height, DEFAULT_TAB_HEIGHT);
//			int type = typedArray.get(R.styleable.BannerView_nav_gravity,);
//			LogUtils.d("type==>" + type);

		} finally {
			typedArray.recycle();
		}

		imgs = new LinkedList<>();
		titles = new LinkedList<>();

		viewpager = new ViewPager(context);
		//设置viewpager的切换时间  默认切换时间为1000 单位毫秒
		setPageChangeDuration(speed);
		//设置viewpager的页面切换监听
		viewpager.addOnPageChangeListener(this);
		viewpager.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		//添加view
		addView(viewpager);
		//创建指示器控件
		radioGroup = new RadioGroup(context);
		radioGroup.setOrientation(RadioGroup.HORIZONTAL);
		LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		//设置下方偏移量
		params.setMargins(0, 0, 0, tabBottomMargin);
		//设置相对属性
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		radioGroup.setLayoutParams(params);
		addView(radioGroup);
	}

//
//	public void setTitles(Collection<String> title) {
//		titles.clear();
//		titles.addAll(title);
//	}

	public void setData(List datas) {
		radioGroup.removeAllViews();
		imgs.clear();
		int length = datas.size();
		if (!titles.isEmpty()) {
			removeView(radioGroup);
			textView = new TextView(context);
			LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(5, 5, 5, 5);
			textView.setTextColor(Color.WHITE);
			textView.setBackgroundColor(Color.parseColor("#4d000000"));
			addView(textView);
			LogUtils.d("添加数据源");
			for (int i = 0; i < length; i++) {
				initImageView(i, datas.get(i));
			}

		} else {
			for (int i = 0; i < length; i++) {
				//这里初始化适配器 初始化指示器  构造view
				RadioButton radioButton = initData(length, i, datas.get(i));
				radioGroup.addView(radioButton);
			}
		}

		bannerAdapter = new BannerAdapter(imgs);
		viewpager.setAdapter(bannerAdapter);
		viewpager.setCurrentItem(DEFAULT_NUMBER / 2);
		if (isPlayStart) {
			startPlay();
		}
	}

	@NonNull
	private RadioButton initData(int length, int i, Object imagepath) {
		initImageView(i, imagepath);
		RadioButton radioButton = new RadioButton(context);
		if (selectTab != 0) {
			radioButton.setBackgroundResource(selectTab);
			radioButton.setButtonDrawable(context.getResources().getDrawable(android.R.color.transparent));
		}
		RadioGroup.LayoutParams buttonParams = new RadioGroup.LayoutParams(tabWidth, tabHeight);
		if (i != length - 1) {
			buttonParams.setMargins(0, 0, tabMargin, 0);
		}
		radioButton.setLayoutParams(buttonParams);
		radioButton.setId(i);
		if (i == 0) radioButton.setChecked(true);
		return radioButton;
	}

	private void initImageView(int i, Object imagepath) {
		View view = null;
		if (loadImageListener == null) {
			view = getDefaultView(imagepath);
		} else {
			view = loadImageListener.onLoadImageListener(viewpager, getView(), imagepath);
		}
		if (onItemClickListener != null) {
			final int finalI = i;
			final View finalView = view;
			RxScheduer.click(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClickListener(finalView, finalI);
				}
			},view);
		}

		imgs.add(view);
	}

	/**
	 * 支持类型 url Integer bitmap
	 *
	 * @param imagePath
	 * @return
	 */
	@NonNull
	private ImageView getDefaultView(Object imagePath) {
		final ImageView imageView = getView();
		if (imagePath instanceof String) {
			imageView.setImageURI(Uri.parse((String) imagePath));
		}
		if (imagePath instanceof Integer) {
			imageView.setImageResource((Integer) imagePath);
		}

		if (imagePath instanceof Bitmap) {
			imageView.setImageBitmap((Bitmap) imagePath);
		}
		return imageView;
	}


	private ImageView getView() {
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		return imageView;
	}

	public void stopPlay() {
		if (disposable != null) disposable.dispose();
		disposable = null;
	}


	/**
	 * 开始播放轮播图
	 */
	public void startPlay() {
		stopPlay();
		isPlayStart = true;
		//默认耗时任务在子线程
		io.reactivex.Observable.interval(intervalTime, TimeUnit.SECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
					}

					@Override
					public void onNext(Long value) {
						//重新设置指示器未知
						int currentItem = viewpager.getCurrentItem();
						viewpager.setCurrentItem(currentItem + 1);
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});

	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		int index = position % imgs.size();
		if (!titles.isEmpty()) {
			textView.setText(titles.get(index));

		} else {

			RadioButton childAt = (RadioButton) radioGroup.getChildAt(index);
			childAt.setChecked(true);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {


	}

	/**
	 * 设置调用setCurrentItem(int item, boolean smoothScroll)方法时，page切换的时间长度
	 *
	 * @param duration page切换的时间长度
	 */
	public void setPageChangeDuration(int duration) {
		try {
			Field scrollerField = viewpager.getClass().getDeclaredField("mScroller");
			scrollerField.setAccessible(true);
			scrollerField.set(viewpager, new BannerScroller(context, duration));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				stopPlay();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (isPlayStart) {
					startPlay();
				}
				break;
		}
		return super.dispatchTouchEvent(ev);
	}


	private class BannerAdapter extends PagerAdapter {
		private LinkedList<View> views;

		public BannerAdapter(Collection<View> views) {
			this.views = new LinkedList<>(views);
		}

		@Override
		public int getCount() {
			return DEFAULT_NUMBER;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = views.get(position % views.size());
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			View view = (View) object;
//			container.removeView(view);
		}
	}


	class BannerScroller extends Scroller {
		private int mDuration = 1000;

		public BannerScroller(Context context, int duration) {
			super(context);
			mDuration = duration;
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
	}


	public interface BannerOnItemClickListener {
		void onItemClickListener(View view, int position);
	}

	public interface LoadImageListener {
		/**
		 * 设置图片加载器
		 *
		 * @param parent    父布局  这里默认是viewpager
		 * @param imageView 默认的imageview
		 * @param url       这里强转成你自己的url
		 * @return
		 */
		View onLoadImageListener(ViewGroup parent, ImageView imageView, Object url);
	}

}
