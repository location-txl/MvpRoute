package com.location.mvp.mvp_route_demo;

import android.util.Log;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.modle.bean.DataBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.util.Collection;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/29 18:39
 * description：
 */

public class MyAdapter extends BaseAdapter<DataBean> {

	public MyAdapter(Collection<DataBean> data, int[] layouts) {
		super(data, layouts);
	}

	@Override
	public void conver(ViewHolder holder, DataBean data, int viewType) {
		switch (viewType) {
			case 0:
				holder.setText(R.id.id_text, data.getMessage());
				break;
			case 1:
				holder.setImageResouce(R.id.item_img, R.drawable.ic_launcher_background);
				break;
		}

	}



	@Override
	protected int getItemType(int position) {
		if (data.get(position).getType() == DataBean.TYPE_TEXT) {
			return 0;
		}
		return 1;
	}

}
