package com.location.mvp.mvp_route_demo.view.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.GroupAdapter;
import com.location.mvp.mvp_route_demo.modle.bean.GroupData;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 13:57
 * description：
 */

public class GroupActivity extends BaseActivity {
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_recycler;
	}

	@Override
	protected void initView() {
		RecyclerView recyclerView = findViewById(R.id.recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 10, Color.parseColor("#000000"));
		dividerItemDecoration.setGroupDivider(30, Color.parseColor("#F05133"));
		recyclerView.addItemDecoration(dividerItemDecoration);
		List<GroupData> dataList = new ArrayList<>();
		dataList.add(new GroupData(1,"321"));
		dataList.add(new GroupData(1,"321"));
		dataList.add(new GroupData(1,"321"));

		dataList.add(new GroupData(2,"321"));
		dataList.add(new GroupData(2,"321"));
		dataList.add(new GroupData(3,"321"));
		dataList.add(new GroupData(3,"321"));
		dataList.add(new GroupData(3,"321"));
		GroupAdapter groupAdapter = new GroupAdapter(dataList, R.layout.item_group);
		recyclerView.setAdapter(groupAdapter);
	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}
}
