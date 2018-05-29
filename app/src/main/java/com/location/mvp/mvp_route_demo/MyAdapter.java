package com.location.mvp.mvp_route_demo;

import android.widget.TextView;

import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/29 18:39
 * description：
 */

public class MyAdapter extends BaseAdapter<String> {
	public MyAdapter(Collection<String> data, int layout) {
		super(data, layout);
	}

	@Override
	public void conver(ViewHolder holder, String data, int viewType) {
		holder.setText(R.id.id_text, data);
	}
}
