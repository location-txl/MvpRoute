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
package com.location.mvp.mvproutelibrary.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.MulitGroupListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class DividerItemDecoration extends RecyclerView.ItemDecoration {
	private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
	// 线性列表 方向
	public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
	public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
	private Drawable mDivider;
	private int mOrientation;
	private Paint mPaint;
	private int mDividerHeight = 2;

	private Paint mGroupPaint;

	private int mGroupHeight;
	private int[] lastLocation = null;

	/**
	 * 默认样式分割线
	 * 宽度为2 颜色为灰色
	 *
	 * @param context
	 * @param orientation
	 */
	public DividerItemDecoration(Context context, @ORIENTATION int orientation) {
		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		mDivider = a.getDrawable(0);
		a.recycle();
		setOrientation(orientation);
	}


	/**
	 * 自定义分割线
	 *
	 * @param context
	 * @param orientation 列表方向
	 * @param drawableId  分割线图片
	 */
	public DividerItemDecoration(Context context, @ORIENTATION int orientation, int drawableId) {
		this(context, orientation);
		mDivider = ContextCompat.getDrawable(context, drawableId);
		mDividerHeight = mDivider.getIntrinsicHeight();
	}

	/**
	 * 自定义分割线
	 *
	 * @param context
	 * @param orientation   列表方向
	 * @param dividerHeight 分割线高度
	 * @param dividerColor  分割线颜色
	 */
	public DividerItemDecoration(Context context, @ORIENTATION int orientation, int dividerHeight, int dividerColor) {
		this(context, orientation);
		mDividerHeight = dividerHeight;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(dividerColor);
		mPaint.setStyle(Paint.Style.FILL);
	}

	public void setGroupDivider(@IntRange(from = 0) int height, int color) {
		mGroupPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGroupHeight = height;
		mGroupPaint.setColor(color);
		mGroupPaint.setStyle(Paint.Style.FILL);

	}

	public void setOrientation(@ORIENTATION int orientation) {
		if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			drawVertical(c, parent);
		} else {
			drawHorizontal(c, parent);
		}
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
	}

	// 绘制垂直排列的分割线
	public void drawVertical(Canvas c, RecyclerView parent) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getWidth() - parent.getPaddingRight();
		final int childCount = parent.getChildCount();
		BaseAdapter adapter = null;
		if (parent.getAdapter() instanceof BaseAdapter) {
			adapter = (BaseAdapter) parent.getAdapter();
		}
//		int groupid = -1;
//		if (adapter != null && adapter.getData(0) != null && adapter.getData(0) instanceof MulitGroupListener) {
//			groupid = ((MulitGroupListener) adapter.getData(0)).bindAdapterGroupId();
//		}
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			int position = parent.getChildAdapterPosition(child);
			if (adapter != null && !adapter.isDrawHeaderFooterLine() && (adapter.isHeaderPos(position) || adapter.isFooterPos(position))) {
				continue;
			}
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int top = child.getBottom() + params.bottomMargin;
			final int bottom = top + mDividerHeight;
			if (adapter != null && adapter.getData(position) instanceof MulitGroupListener) {
				if (position == adapter.getlastGroupPosition(position)) {
					drawGroupDivider(c, left, right, top, top+mGroupHeight);
				} else {
					drawcommonDivider(c, left, right, top, bottom);
				}

			} else {
				drawcommonDivider(c, left, right, top, bottom);
			}
		}
	}

	private void drawcommonDivider(Canvas c, int left, int right, int top, int bottom) {
		if (mDivider != null) {
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
		if (mPaint != null) {
			c.drawRect(left, top, right, bottom, mPaint);
		}
	}

	private void drawGroupDivider(Canvas c, int left, int right, int top, int bottom) {

		if (mGroupPaint != null) {
			c.drawRect(left, top, right, bottom, mGroupPaint);
		}
	}

	// 绘制水平排列的分割线
	public void drawHorizontal(Canvas c, RecyclerView parent) {
		final int top = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();
		final int childCount = parent.getChildCount();
		BaseAdapter adapter = null;
		if (parent.getAdapter() instanceof BaseAdapter) {
			adapter = (BaseAdapter) parent.getAdapter();
		}
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			int position = parent.getChildAdapterPosition(child);
			if (adapter != null && !adapter.isDrawHeaderFooterLine() && (adapter.isHeaderPos(position) || adapter.isFooterPos(position))) {
				continue;
			}
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int left = child.getRight() + params.rightMargin;
			final int right = left + mDividerHeight;
			drawcommonDivider(c, left, right, top, bottom);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			outRect.set(0, 0, 0, mDividerHeight);
		} else {
			outRect.set(0, 0, mDividerHeight, 0);
		}
	}

	@IntDef({VERTICAL_LIST, HORIZONTAL_LIST})
	@Retention(RetentionPolicy.SOURCE)
	public @interface ORIENTATION {
	}

}
