package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;

import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/16 17:41
 * description：
 */

public class MessAAdapter  extends RecyclerView.Adapter<MessAAdapter.ViewHolder>{
private List<NoMessageBean> data;
private @LayoutRes int layout;

	public MessAAdapter(List<NoMessageBean> data, int layout) {
		this.data = data;
		this.layout = layout;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view  = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
         holder.textView.setText(data.get(position).getName());
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	class  ViewHolder extends RecyclerView.ViewHolder{
TextView textView;
		public ViewHolder(View itemView) {
			super(itemView);
			textView  = itemView.findViewById(R.id.item_content);
		}
	}
}
