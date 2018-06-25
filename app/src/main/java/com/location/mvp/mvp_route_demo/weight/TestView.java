package com.location.mvp.mvp_route_demo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.location.mvp.mvp_route_demo.R;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/25 11:37
 * description：
 */

public class TestView extends LinearLayout {
private View view;
private Context context;
	public TestView(Context context) {
		this(context,null);
	}

	public TestView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	public void setView(View view){
		this.view = view;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		view.draw(canvas);
//		View inflate = inflate(context, R.layout.test_view, this);
//		addView(inflate);
		view.draw(canvas);
	}
}
