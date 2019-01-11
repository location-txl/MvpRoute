package com.location.mvp.mvproutelibrary.adapter;

import android.view.View;

/**
 * @author tianxiaolong
 *         time：2019/1/11 12:56
 *         description：
 */

public interface OnGroupItemClickListener {
	void onGroupItemClick( View itemView, int groupPosition);

	void onChildItemClick( View itemView, int groupPosition, int childPosition);
}
