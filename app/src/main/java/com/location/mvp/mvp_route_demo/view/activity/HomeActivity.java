package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.HomeAdapter;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.view.activity.adapteractivity.HomeAdaptrActivity;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/7/21 0021 16:46
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class HomeActivity extends BaseToActivity implements OnItemClickListener {
	private RecyclerView recyclerView;
	private HomeAdapter homeAdapter;

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_home;
	}

	@Override
	protected String getTooBarTitle() {
		return "Mvproute简易的项目框架";
	}

	@Override
	protected void initView() {
		super.initView();
		recyclerView = findViewById(R.id.home_RecyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
				.VERTICAL_LIST, 5, Color.parseColor("#999999")));

		homeAdapter = new HomeAdapter(R.layout.item_home);
		recyclerView.setAdapter(homeAdapter);
		homeAdapter.setOnItemClickListener(this);


	}

	@Override
	protected void loadData() {
		String[] stringArray = getResources().getStringArray(R.array.home_list);
		List<String> list = Arrays.asList(stringArray);
		homeAdapter.refresh(list);
	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}


	@Override
	public void onItemClick(ViewHolder viewHolder, View view, int position) {
		switch (position) {
			case 0:
				startActivity(HomeAdaptrActivity.class);
				break;
			case 1:
				break;
			case 2:
				//Spanutils
				startActivity(SpanActivity.class);
				break;
			case 6:
				//activity回传分发
				startActivity(new Intent(this, ResultActivity.class));
				break;
			case 7:
				startActivity(NetActivity.class);
				break;
			case 8:
				startActivity(ZcActivity.class);
				break;
			default:
				LogUtils.d("未知");
		}
	}
}
