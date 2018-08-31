package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/3 11:45
 * description：
 */

public class AdapterHome extends BaseAdapter<String> {
	public AdapterHome(Collection<String> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable String data, int viewType) {
		holder.setText(R.id.home_text, data);
	}

	@Override
	public void onBindHeaderViewHolder(ViewHolder viewHolder, @Nullable Object response, int layout) {
		super.onBindHeaderViewHolder(viewHolder, response, layout);
		switch (layout) {
			case R.layout.header_view:
				viewHolder.setText(R.id.header_text, (String) response);
				break;
			default:
		}
	}
}
