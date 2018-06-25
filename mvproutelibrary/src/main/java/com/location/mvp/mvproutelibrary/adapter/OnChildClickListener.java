package com.location.mvp.mvproutelibrary.adapter;

import android.view.View;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/29 19:15
 * description：
 */

public interface OnChildClickListener {
	/**
	 * 子view的点击事件
	 * @param viewHolder viewholer
	 * @param view    点击的view
	 * @param position 当前的索引
	 */
	void onChildClcik(ViewHolder viewHolder, View view, int position);
}
