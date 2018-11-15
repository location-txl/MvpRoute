package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.OnItemListener;
import com.location.mvp.mvp_route_demo.adapter.ZcDataAdapter;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.ZcChildBean;
import com.location.mvp.mvp_route_demo.bean.ZcDataBean;
import com.location.mvp.mvp_route_demo.contract.ZcContract;
import com.location.mvp.mvp_route_demo.presenter.ZcPresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.base.Request;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 19:46
 * description：
 */
@Layout(R.layout.activity_ac_selectview)
public class ZcActivity extends BaseToActivity<ZcContract.Presenter> implements  ZcContract.View, OnItemListener {
	private final int CODE = 201;
	public static final String EXTRA_DATA = "data_s_a";
	public static final String EXTRA_POSITION = "data_s_position";
	private RecyclerView recyclerView;

	private ZcDataAdapter zcDataAdapter;
	private ArrayList<ZcDataBean> data;
	private ArrayList<ZcChildBean> selectList;

	@Override
	public void onshowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "zc选择view";
	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		selectList = new ArrayList<>();
		recyclerView = findViewById(R.id.zc_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		data = new ArrayList<>();
		zcDataAdapter = new ZcDataAdapter(data, R.layout.item_data, this);
		recyclerView.setAdapter(zcDataAdapter);
		findViewById(R.id.zc_add).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				presenter.insertItem();
			}
		});
		presenter.insertItem();
	}


	@Request(request = CODE)
	private void showResult(Intent intent) {
		ZcChildBean childBean = intent.getParcelableExtra(EXTRA_DATA);
		int extra_position = intent.getIntExtra(EXTRA_POSITION, -1);
		if (extra_position == -1) {
			return;
		}
		selectList.add(childBean);
		ZcDataBean zcDataBean = zcDataAdapter.getItem(extra_position);
		zcDataBean.setChildBean(childBean);
		zcDataAdapter.notifyItemChanged(extra_position);
	}

	@Override
	protected void loadData() {
		presenter.loadData();
	}

	@NonNull
	@Override
	protected ZcContract.Presenter createPresenter() {
		return new ZcPresenter();
	}


	@Override
	public void showData(List<ZcDataBean> response) {
	}

	@Override
	public void insertItemSuccful(ZcDataBean zcDataBean) {
		zcDataAdapter.loadItem(zcDataBean);
	}
	/**
	 *  分为选择过的跳转和 没选数据的跳转，选择过的就不进行任何选择数据
	 * @param position
	 */
	@Override
	public void onItemClick(RecyclerView.ViewHolder holder, int position) {
		if(position==zcDataAdapter.getItemList().size()-1||zcDataAdapter.getItem(position).getChildBean()==null){
			if(position==zcDataAdapter.getItemList().size()-1&&zcDataAdapter.getItem(position).getChildBean()!=null){
				selectList.remove(selectList.size()-1);
			}
			presenter.openChildActivity(this, selectList, CODE, position);
			return;
		}
		if(zcDataAdapter.getItem(position).getChildBean()!=null){
			presenter.openChildActivity(this, selectList);
		}
	}
}
