package com.location.mvp.mvp_route_demo.view.activity.adapteractivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.AdapterHome;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.ItemTypeResponse;
import com.location.mvp.mvproutelibrary.adapter.OnHeaderClickListener;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/1 18:18
 * description：
 */

public class HomeAdaptrActivity extends BaseToActivity {
	private RecyclerView recyclerView;
	private AdapterHome adapterHome;

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected void initView() {
		super.initView();
		recyclerView = findViewById(R.id.home_adapter_recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		List<ItemTypeResponse> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			ItemTypeResponse response = new ItemTypeResponse(i % 2, "测试" + i);
			list.add(response);
		}
		adapterHome = new AdapterHome(list, R.layout.item_home);
		//绑定多布局
		adapterHome.addType(0, R.layout.item_home);
		adapterHome.addType(1, R.layout.item_button);

		adapterHome.addHeaderView("123", R.layout.header_test_view);
		adapterHome.addHeaderView("123", R.layout.header_test_view);
		adapterHome.addFooterView("123", R.layout.header_test_view);
		adapterHome.addFooterView("123", R.layout.header_test_view);
		adapterHome.addFooterView("123", R.layout.header_test_view);

		recyclerView.setAdapter(adapterHome);
		adapterHome.setOnHeaderClickListener(new OnHeaderClickListener() {
			@Override
			public void onHeaderClick(int layout, View view, @Nullable Object data, int position, boolean isHeader) {
				ToastUtils.showShort("点击头尾布局了");
			}
		});
		adapterHome.setOnItemClickListener(new OnItemClickListener<AdapterHome.HomdHolderBase>() {
			@Override
			public void onItemClick(AdapterHome.HomdHolderBase viewHolder, View view, int position) {
          ToastUtils.showShort("点击item===>"+position);
			}
		});
	}

	@Override
	protected String getTooBarTitle() {
		return "功能强大的RecyclerView适配器";
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_adapter_home;
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
