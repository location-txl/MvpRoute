package com.location.mvp.mvp_route_demo.view.activity.adapteractivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.AdapterHome;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.ItemTypeResponse;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.adapter.OnHeaderClickListener;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/1 18:18
 * description：
 */
@Layout(R.layout.activity_adapter_home)
public class HomeAdaptrActivity extends BaseToActivity {
	private RecyclerView recyclerView;
	private AdapterHome adapterHome;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
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
				ToastUtils.showShort("点击item===>" + position);
			}
		});
		final Button view = findViewById(R.id.test_clcik);
		RxScheduer.click( new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				RxScheduer.countDown(view, 1, new RxScheduer.CountDownListener<Button>() {
					@Override
					public void onBindCountDown(Button view) {
						view.setEnabled(false);
					}

					@Override
					public void onCountDownProgress(Button view, int second) {
						view.setText(String.format("%d秒", second));
					}

					@Override
					public void onCountDownComplete(Button view) {
						view.setText("点击发送");
						view.setEnabled(true);
					}
				});
			}
		},view);

	}

	@Override
	protected String getTooBarTitle() {
		return "功能强大的RecyclerView适配器";
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
