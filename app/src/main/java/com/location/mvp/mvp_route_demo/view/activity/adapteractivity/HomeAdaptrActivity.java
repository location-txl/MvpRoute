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
 * 项目:趣租部落
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
		adapterHome.addHeaderView("20", R.layout.header_view);
		adapterHome.addHeaderView(R.layout.header_test_view);
		adapterHome.addHeaderView("20", R.layout.header_view);
		adapterHome.addHeaderView("20", R.layout.header_view);
		recyclerView.setAdapter(adapterHome);
		findViewById(R.id.test_clcik).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random random = new Random();
				int i = random.nextInt(100);
				String data = String.valueOf(i);
				List<String> list1= new ArrayList<>();
				list1.add(data);
				list1.add(data);
				list1.add(data);

				adapterHome.updateHeader(list1,R.layout.header_view,0,2,1);
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
