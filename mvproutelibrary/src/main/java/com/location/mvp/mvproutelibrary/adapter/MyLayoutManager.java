package com.location.mvp.mvproutelibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/31 13:35
 * description：
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {
	private int verticalScrollOffset = 0;
	private int totalHeight = 0;
	@Override
	public RecyclerView.LayoutParams generateDefaultLayoutParams() {
		return null;
	}

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		// 跳过preLayout，preLayout主要用于支持动画
		if (state.isPreLayout()) {
			return;
		}
//		super.onLayoutChildren(recycler, state);
		//在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
		detachAndScrapAttachedViews(recycler);
		//定义竖直方向的偏移量
		int offsetY = 0;
		totalHeight = 0;
		for (int i = 0; i < getItemCount(); i++) {
			//这里就是从缓存里面取出
			View view = recycler.getViewForPosition(i);
			//将View加入到RecyclerView中
			addView(view);
			//对子View进行测量
			measureChildWithMargins(view, 0, 0);
			//把宽高拿到，宽高都是包含ItemDecorate的尺寸
			int width = getDecoratedMeasuredWidth(view);
			int height = getDecoratedMeasuredHeight(view);
			//最后，将View布局
			layoutDecorated(view, 0, offsetY, width, offsetY + height);
			//将竖直方向偏移量增大height
			offsetY += height;
			totalHeight += height;
		}
		totalHeight = Math.max(totalHeight, getVerticalSpace());
	}
	private int getVerticalSpace() {
		return getHeight() - getPaddingBottom() - getPaddingTop();
	}
	@Override
	public boolean canScrollVertically() {
		return true;
	}

	@Override
	public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
		//实际要滑动的距离
		int travel = dy;

		//如果滑动到最顶部
		if (verticalScrollOffset + dy < 0) {
			travel = -verticalScrollOffset;
		} else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
			travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
		}

		//将竖直方向的偏移量+travel
		verticalScrollOffset += travel;

		// 平移容器内的item
		offsetChildrenVertical(-travel);

		return travel;


	}
}
