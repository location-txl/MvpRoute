package com.location.mvp.adapter;

import androidx.annotation.Nullable;

import com.location.mvp.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;

/**
 * @author tianxiaolong
 * time：2019-08-25 14:57
 * description：
 */
public class HomeAdapter extends BaseAdapter<String, BaseViewHolder> {
	public HomeAdapter(int layout) {
		super(layout);
	}

	@Override
	public void conver(BaseViewHolder holder, @Nullable String data, int viewType) {
		holder.setText(R.id.item_content,data);
	}
}
