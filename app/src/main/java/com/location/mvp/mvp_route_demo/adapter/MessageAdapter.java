package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;

import java.util.Collection;

/**
 * 项目:Mvp_Route_Demo
 *
 * @author：location time：2018/7/12 17:26
 * description：
 */

public class MessageAdapter extends BaseAdapter<NoMessageBean,BaseViewHolder> {
	public MessageAdapter(Collection<NoMessageBean> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(BaseViewHolder holder, @Nullable NoMessageBean data, int viewType) {
		holder.setText(R.id.item_content, data.getName());

	}

	@Override
	public void onBindHeaderViewHolder(BaseViewHolder viewHolder, @Nullable Object response, int layout) {

	}
}
