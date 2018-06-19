package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/14 15:40
 * description：
 */

public interface OnHeaderClickListener {
	/**
	 *headerFotter点击事件
	 * @param layout  布局ID
	 * @param view view
	 * @param data  数据源
	 * @param position  当前坐标索引 不是RecyclerView的索引 而是相对于这个布局
	 * @param isHeader  是否是头布局
	 */
	void onHeaderClick( int layout, View view, @Nullable Object data, int position, boolean isHeader);
}
