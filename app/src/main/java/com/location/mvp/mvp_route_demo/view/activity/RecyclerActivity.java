package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.location.mvp.mvp_route_demo.MyAdapter;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseThrowable;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/29 18:14
 * description：
 */

public class RecyclerActivity extends BaseActivity {
	private List<String> strings;
private RecyclerView recyclerView;
	@Override
	public void onshowError(BaseThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_recycler;
	}

	@Override
	protected void initView() {
		strings = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			strings.add("年后");
		}
		recyclerView = findViewById(R.id.recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new MyAdapter(strings,R.layout.item_text));
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
