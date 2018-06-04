package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.location.mvp.mvp_route_demo.MyAdapter;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.contract.RecyclerContract;
import com.location.mvp.mvp_route_demo.modle.bean.DataBean;
import com.location.mvp.mvp_route_demo.presenter.RecyclerPresenter;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.adapter.OnChildListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/29 18:14
 * description：
 */

public class RecyclerActivity extends BaseActivity<RecyclerPresenter> implements RecyclerContract.View, View.OnClickListener {
	private RecyclerView recyclerView;
	private MyAdapter myAdapter;


	@Override
	public void onshowError(BaseThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_recycler;
	}

	@Override
	protected void initView() {
		findViewById(R.id.recy_add).setOnClickListener(this);
		findViewById(R.id.recy_remove).setOnClickListener(this);
		findViewById(R.id.recy_clear).setOnClickListener(this);
		recyclerView = findViewById(R.id.recy);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
//		recyclerView.setLayoutManager(new MyLayoutManager());
//		recyclerView.setLayoutManager(new StaggeredGridLayoutManager());
		presenter.loadData();

	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected RecyclerPresenter createPresenter() {
		return new RecyclerPresenter();
	}

	@Override
	public void showData(List<DataBean> data) {

		int[] layouts = new int[]{R.layout.item_text, R.layout.item_image};
		myAdapter = new MyAdapter(data, layouts);
		View emptuView = LayoutInflater.from(this).inflate(R.layout.item_empty_view, null);
		myAdapter.setEmptyView(emptuView);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addHeaderView(R.drawable.ic_launcher_background,R.layout.header_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.addFooterView(123, R.layout.footer_view);
		myAdapter.setChildOnClickListener(R.id.item_img, new OnChildListener() {
			@Override
			public void onChildClcikListener(ViewHolder viewHolder, View view, int position) {
				Toast.makeText(RecyclerActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
			}
		});
		recyclerView.setAdapter(myAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.recy_add:
				List<DataBean> list = new ArrayList<>();
				list.add(new DataBean(DataBean.TYPE_TEXT, "鲁智深"));
				list.add(new DataBean(DataBean.TYPE_TEXT, "祖冲之"));
				myAdapter.loadItem(list);
				break;
			case R.id.recy_clear:
				myAdapter.clear();
				break;
			case R.id.recy_remove:
				myAdapter.remove(0);
				break;


		}
	}
}
