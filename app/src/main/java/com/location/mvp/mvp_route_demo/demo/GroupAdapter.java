package com.location.mvp.mvp_route_demo.demo;

import android.support.annotation.Nullable;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 17:38
 * description：
 */

public class GroupAdapter extends BaseAdapter<GroupBean> {
	public GroupAdapter(Collection<GroupBean> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable GroupBean data, int viewType) {
		holder.setText(R.id.item_name, data.getMessgae());
		holder.findViewById(R.id.item_btn).setVisibility(View.GONE);
	}
}
