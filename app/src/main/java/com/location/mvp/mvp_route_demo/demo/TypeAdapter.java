package com.location.mvp.mvp_route_demo.demo;

import android.support.annotation.Nullable;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 16:15
 * description：
 */

public class TypeAdapter extends BaseAdapter<TypeBean> {

	public TypeAdapter(Collection<TypeBean> data, int layout) {
		super(data, layout);
	}


	//绑定数据源
	@Override
	public void conver(ViewHolder holder, @Nullable TypeBean data, int viewType) {
		switch (viewType) {
			case TypeBean.TYPE_IMG:
				holder.setImageResouce(R.id.item_img, R.drawable.ic_launcher_background);
				break;
			case TypeBean.TYPE_TEXT:
				holder.setText(R.id.item_name, data.getMessage());
				break;
		}
	}

	//绑定头布局  按需重写
	@Override
	public void onBindHeaderViewHolder(ViewHolder viewHolder, @Nullable Object response, int layout) {
		switch (layout){
			case R.layout.header_text_view:
				viewHolder.setText(R.id.header_text, (String) response);
				break;


		}
	}


}
