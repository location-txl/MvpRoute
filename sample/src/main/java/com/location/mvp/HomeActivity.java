package com.location.mvp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.location.mvp.adapter.HomeAdapter;
import com.location.mvp.base.BaseToActivity;
import com.location.mvp.contract.HomeContract;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.presebter.HomePresenter;

import java.util.List;

@Layout(R.layout.activity_home)
public class HomeActivity extends BaseToActivity<HomeContract.HomePresenter> implements HomeContract.HomeView{


	@Override
	protected String getTooBarTitle() {
		return "测试";
	}


	private RecyclerView recyclerView;

	private HomeAdapter adapter;
	@Override
	protected void loadData() {
		presenter.loadList(getApplicationContext());
	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		recyclerView  = findViewById(R.id.home_list);
		recyclerView.setLayoutManager(new GridLayoutManager(this,2));
		adapter = new HomeAdapter(R.layout.item_grid);
		adapter.setOnItemClickListener((viewHolder, view, position) -> {

		});
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected HomeContract.HomePresenter createPresenter() {
		return new HomePresenter();
	}

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	public void showList(List<String> list) {
       adapter.refresh(list);
	}
}
