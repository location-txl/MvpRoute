package com.location.mvp.mvp_route_demo.view.activity.adapteractivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.AdapterHome;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

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
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add("测试");
		}
		adapterHome = new AdapterHome(list, R.layout.item_home);
//		adapterHome.addHeaderView("20", R.layout.header_view);
		adapterHome.addHeaderView("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536907624520&di=f158e113581936a518a00b1705cf3662&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F838ba61ea8d3fd1f274bd7133d4e251f95ca5f5c.jpg",R.layout.header_test_view);
		adapterHome.addHeaderView("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536907624544&di=23454473f3d04d1f3b95f23aef8417eb&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd000baa1cd11728becf9b70ec5fcc3cec2fd2cc1.jpg",R.layout.header_test_view);
//		adapterHome.addHeaderView("20", R.layout.header_view);
//		adapterHome.addHeaderView("20", R.layout.header_view);
		recyclerView.setAdapter(adapterHome);

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
