package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.ItemTypeResponse;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/3 11:45
 * description：
 */

public class AdapterHome extends BaseAdapter<ItemTypeResponse> {
	public AdapterHome(Collection<ItemTypeResponse> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable ItemTypeResponse data, int viewType) {
		switch (viewType){
			case 0:
				holder.setText(R.id.home_text, data.getContent());
				break;
			case 1:
				holder.setText(R.id.home_button, data.getContent());
				break;
			default:
		}
	}

	@Override
	public void onBindHeaderViewHolder(ViewHolder viewHolder, @Nullable Object response, int layout) {
		super.onBindHeaderViewHolder(viewHolder, response, layout);
		switch (layout) {
			case R.layout.header_test_view:
				ImageView imageView = viewHolder.findViewById(R.id.header_imageview);
				Glide.with(viewHolder.getContext()).load((String)response).into(imageView);
				break;
			default:
		}
	}
}
