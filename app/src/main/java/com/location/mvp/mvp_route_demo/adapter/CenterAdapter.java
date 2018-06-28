package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;
import android.widget.AdapterView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/27 18:07
 * description：
 */

public class CenterAdapter extends BaseAdapter<String> {


	public CenterAdapter(int layout) {
		super(layout);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable String data, int viewType) {
		holder.setText(R.id.center_text, data);
	}
}
