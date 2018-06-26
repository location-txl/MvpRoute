package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.PictureBean;

import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/26 10:15
 * description：
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {


	private List<PictureBean> list;
	private @LayoutRes
	int layout;
	private onPictureClick onPictureClick;

	public PictureAdapter(List<PictureBean> list, int layout, PictureAdapter.onPictureClick onPictureClick) {
		this.list = list;
		this.layout = layout;
		this.onPictureClick = onPictureClick;
	}


	@Override
	public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
		return new PictureViewHolder(view);
	}

	@Override
	public void onBindViewHolder(PictureViewHolder holder, final int position) {


		if (position >= list.size()) {
			holder.deleteImg.setVisibility(View.GONE);
			holder.imageView.setImageResource(R.drawable.photo);
		} else {
			PictureBean pictureBean = list.get(position);
			holder.deleteImg.setVisibility(View.VISIBLE);
			Glide.with(holder.imageView.getContext()).load(pictureBean.getLocaUrl()).into(holder.imageView);
			holder.deleteImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onPictureClick.onDeletePicture(position);
				}
			});
		}
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (position >= list.size()) {
					onPictureClick.onSelectPicture(position);
				} else {
					onPictureClick.onPriewPicture(position);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		if (list.size() >= 9) {
			return list.size();
		} else {
			return list.size() + 1;
		}
	}

	class PictureViewHolder extends RecyclerView.ViewHolder {
		View itemView;
		ImageView imageView;
		ImageView deleteImg;

		public PictureViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			imageView = itemView.findViewById(R.id.item_picture);
			deleteImg = itemView.findViewById(R.id.item_delete);
		}
	}

	public interface onPictureClick {

		void onPriewPicture(int position);

		void onDeletePicture(int position);

		void onSelectPicture(int position);
	}
}
