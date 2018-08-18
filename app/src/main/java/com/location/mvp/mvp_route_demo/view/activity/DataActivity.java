package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.MessageAdapter;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvproutelibrary.adapter.OnChildClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 项目:Mvp_Route_Demo
 *
 * @author：location time：2018/7/12 17:22
 * description：
 */

public class DataActivity extends AppCompatActivity implements OnChildClickListener {
	private RecyclerView recyclerView;
	private List<NoMessageBean> data;
	private MessageAdapter adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		recyclerView = findViewById(R.id.id_recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		data = new ArrayList<>();
		adapter = new MessageAdapter(data, R.layout.item_message);
		adapter.setOnChildClickListener(R.id.item_delete, this);
		recyclerView.setAdapter(adapter);
		for (int i = 0; i < 50; i++) {
			data.add(new NoMessageBean("测试" + i, i));
		}
		adapter.refresh(data);

	}

	/**
	 * 删除
	 *
	 * @param viewHolder viewholer
	 * @param view       点击的view
	 * @param position   当前的索引
	 */
	@Override
	public void onChildClcik(ViewHolder viewHolder, View view, int position) {
		delete(position);
	}

	private void delete(final int position) {
		//模拟耗时操作
		Observable.timer(2, TimeUnit.SECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						adapter.remove(position);
						Toast.makeText(DataActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
					}
				});

	}
}
