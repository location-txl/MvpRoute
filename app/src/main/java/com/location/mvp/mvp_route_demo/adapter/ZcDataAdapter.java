package com.location.mvp.mvp_route_demo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.ZcChildBean;
import com.location.mvp.mvp_route_demo.bean.ZcDataBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.Collection;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 20:02
 * description：
 */

public class ZcDataAdapter extends RecyclerView.Adapter<ZcDataAdapter.MyViewHolder> {
private List<ZcDataBean> dataBeanList;
private @LayoutRes int layout;
private OnItemListener onItemClickListener;

	public ZcDataAdapter(List<ZcDataBean> dataBeanList, int layout, OnItemListener onItemClickListener) {
		this.dataBeanList = dataBeanList;
		this.layout = layout;
		this.onItemClickListener = onItemClickListener;
	}


	public ZcDataBean getItem(int posittion){
		return dataBeanList.get(posittion);
	}
	public List<ZcDataBean> getItemList(){
		return dataBeanList;
	}

	public void loadItem(ZcDataBean zcDataBean){
		dataBeanList.add(zcDataBean);
		notifyItemInserted(dataBeanList.size());
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		if(onItemClickListener!=null){
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClick(holder,position);
				}
			});
		}
		ZcDataBean data = dataBeanList.get(position);
		if (data.getChildBean() == null) {
			holder.textView.setHint("请选择属性");
			holder.textView.setText("");
		}else{
			holder.textView.setText(data.getChildBean().getChildTitle());
		}
	}

	@Override
	public int getItemCount() {
		return dataBeanList.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
View itemView;
		TextView textView;
		public MyViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			textView=  itemView.findViewById(R.id.item_title);
		}
	}


}
