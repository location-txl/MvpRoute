package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;

import com.location.mvp.mvp_route_demo.modle.bean.GroupData;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 14:03
 * description：
 */

public class GroupAdapter extends BaseAdapter<GroupData> {
	public GroupAdapter(Collection<GroupData> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable GroupData data, int viewType) {

	}
}
