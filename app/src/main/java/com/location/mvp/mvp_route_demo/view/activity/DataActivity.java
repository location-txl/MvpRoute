package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/27 20:49
 * description：
 */

public class DataActivity extends BaseActivity {
	private RecyclerView recyclerView;



	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_data;
	}

	@Override
	protected void initView() {
		recyclerView = findViewById(R.id.data_recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new Divider());
		List<String> list = new ArrayList<>();
		for(int i=0;i<50;i++){
			list.add("测试i" + i);
		}
		Myadapter myadapter = new Myadapter(list, R.layout.item_left);
		recyclerView.setAdapter(myadapter);
	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	class Myadapter extends BaseAdapter<String> {

		public Myadapter(Collection<String> data, int layout) {
			super(data, layout);
		}

		@Override
		public void conver(ViewHolder holder, @Nullable String data, int viewType) {
			holder.setText(R.id.item_left_title, data);

		}
	}
}
