package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.PictureAdapter;
import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvp_route_demo.contract.PictureContract;
import com.location.mvp.mvp_route_demo.presenter.PicturePresenter;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.base.Request;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/7/2 0002 20:40
 * 修改人:
 * 修改内容:
 * 修改时间:
 */

@Layout(R.layout.activity_picture)
public class PictureActivity extends BaseActivity<PictureContract.Presenter> implements
		PictureContract.View, OnItemClickListener {
	private RecyclerView recyclerView;
	private List<PictureBean.ResultsBean> data;
	private PictureAdapter adapter;

	@Override
	public void onshowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}


	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		recyclerView = findViewById(R.id.id_recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		data = new ArrayList<>();
		adapter = new PictureAdapter(data, R.layout.item_picture, this);
		recyclerView.setAdapter(adapter);


	}

	@Override
	protected void loadData() {
		presenter.loadPicture();
	}


	@Request(request = 123)
	public void refreshImage(Intent intent) {
		adapter.refresh();
	}

	@Request(request = 990, result = -1)
	public void finsh(Intent intent) {
		finish();
	}




	@NonNull
	@Override
	protected PictureContract.Presenter createPresenter() {
		return new PicturePresenter();
	}

	@Override
	public void showData(List<PictureBean.ResultsBean> response) {
		data.addAll(response);
		adapter.refresh(data);
	}

	@Override
	public void onItemClick(BaseViewHolder viewHolder, View view, int position) {
		startActivityForResult(new Intent(this, TestActivity.class), 123);
	}
}
