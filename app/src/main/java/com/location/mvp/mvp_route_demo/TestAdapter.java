package com.location.mvp.mvp_route_demo;

import android.support.annotation.Nullable;

import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/28 22:28
 * description：
 */

public class TestAdapter extends BaseAdapter<OerBean> {

	public TestAdapter(int layout) {
		super(layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable OerBean data, int viewType) {
		switch (viewType){
			case 1:
				holder.setText(R.id.item_title,data.getName());
				break;
			case 2:
				holder.setText(R.id.item_content,data.getName());
				break;

		}
	}
}
