package com.location.mvp.mvp_route_demo.adapter;

import android.support.annotation.Nullable;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/7/21 0021 16:53
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class HomeAdapter extends BaseAdapter<String,BaseViewHolder> {


    public HomeAdapter(int layout) {
        super(layout);
    }

    @Override
    public void conver(BaseViewHolder holder, @Nullable String data, int viewType) {
        holder.setText(R.id.home_text, data);
    }
}
