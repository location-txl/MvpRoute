package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:Mvp_Route_Demo
 *
 * @author：location time：2018/7/12 17:26
 * description：
 */

public class MessageAdapter extends BaseAdapter<NoMessageBean> {
	public MessageAdapter(Collection<NoMessageBean> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable NoMessageBean data, int viewType) {
		holder.setText(R.id.item_content, data.getName());
		Glide.with(holder.getContext()).load("dsa").into(new ImageView(holder.getContext()));
	}
}
