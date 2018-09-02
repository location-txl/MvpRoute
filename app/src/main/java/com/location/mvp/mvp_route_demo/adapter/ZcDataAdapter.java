package com.location.mvp.mvp_route_demo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
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

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 20:02
 * description：
 */

public class ZcDataAdapter extends BaseAdapter<ZcDataBean> {

	public ZcDataAdapter(Collection<ZcDataBean> data, int layout, OnItemClickListener listener) {
		super(data, layout, listener);
	}

	@Override
	public void conver(ViewHolder holder, @Nullable ZcDataBean data, int viewType) {
		TextView textView = holder.findViewById(R.id.item_title);
		if (data.getChildBean() == null) {
			textView.setHint("请选择属性");
			textView.setText("");
		}else{
			textView.setText(data.getChildBean().getChildTitle());
		}


	}


}
