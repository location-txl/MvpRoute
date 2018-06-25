package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/25 16:22
 * description：
 */

public class PhotoAdapter extends BaseAdapter<PictureBean> {
	public PhotoAdapter(Collection<PictureBean> data, int layout, AdapterView.OnItemClickListener listener) {
		super(data, layout, listener);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable PictureBean data, int viewType) {
		if (data == null) {
			holder.setImageResouce(R.id.item_picture, R.drawable.photo);
			holder.setGone(R.id.item_delete);
		} else {
			holder.setVisibility(R.id.item_delete);
			ImageView imageView = holder.findViewById(R.id.item_picture);
			Glide.with(holder.getItemView().getContext()).load(data.getLocaUrl()).into(imageView);
		}
	}

	@Override
	public int getItemCount() {
		return super.getItemCount() + (data.size() == 9 ? 0 : 1);
	}
}
