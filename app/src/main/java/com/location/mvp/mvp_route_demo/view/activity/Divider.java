package com.location.mvp.mvp_route_demo.view.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.location.mvp.mvp_route_demo.R;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/27 20:54
 * description：
 */

public class Divider extends RecyclerView.ItemDecoration {
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);


		//获取单条的数目
		int itemCount = state.getItemCount();
		int childCount = parent.getChildCount();
		//得出距离左边和右边的padding
		int left = parent.getPaddingLeft();
		int right = parent.getWidth() - parent.getPaddingRight();
		View view = parent.getChildAt(0);
		int position = parent.getChildAdapterPosition(view);

		int viewBottom = view.getBottom();
		//top 决定当前顶部第一个悬浮Group的位置
		int top = Math.max(20, view.getTop());


		//根据position获取View
		View groupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center, null);
		if (groupView == null) {
			return;
		}
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 20);
		groupView.setLayoutParams(layoutParams);
		groupView.setDrawingCacheEnabled(true);
		groupView.measure(
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		//指定高度、宽度的groupView
		groupView.layout(0, 0, right, 50);
		groupView.buildDrawingCache();
		Bitmap bitmap = groupView.getDrawingCache();
//			int marginLeft = isAlignLeft ? 0 : right - groupView.getMeasuredWidth();
		c.drawBitmap(bitmap, left, top - 50, null);
	}
}
