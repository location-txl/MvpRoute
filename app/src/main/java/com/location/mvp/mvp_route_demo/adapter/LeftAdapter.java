package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.MulitBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/27 18:04
 * description：
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {
	private List<MulitBean> list;

	private OnItemClickListener listener;



	public LeftAdapter(List<MulitBean> list, OnItemClickListener listener) {
		this.list = list;
		this.listener = listener;
	}

	@Override
	public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new LeftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false));
	}

	@Override
	public void onBindViewHolder(final LeftViewHolder holder, final int position) {
		int lastPosition = holder.itemView.getTag() != null ? (int) holder.itemView.getTag() : -1;
		if (lastPosition == holder.getAdapterPosition()) {
			holder.setVis();
		} else {
			holder.setGone();
		}
		holder.textView.setText(list.get(position).getTitle());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 listener.onItemClick(holder,holder.itemView,position);

			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class LeftViewHolder extends RecyclerView.ViewHolder {
		TextView textView;
		View leftView;
		public View itemView;

		public LeftViewHolder(View itemView) {
			super(itemView);
			textView = itemView.findViewById(R.id.item_left_title);
			leftView = itemView.findViewById(R.id.left_view);
			this.itemView = itemView;
		}

		public void setVis() {
			leftView.setVisibility(View.VISIBLE);
		}

		public void setGone() {
			leftView.setVisibility(View.GONE);
		}

	}

	public interface OnItemClickListener{
		void onItemClick(LeftViewHolder viewHolder, View view, int position);
	}
}
