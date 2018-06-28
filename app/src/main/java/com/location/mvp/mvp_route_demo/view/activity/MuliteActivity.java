package com.location.mvp.mvp_route_demo.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.CenterAdapter;
import com.location.mvp.mvp_route_demo.adapter.LeftAdapter;
import com.location.mvp.mvp_route_demo.bean.MulitBean;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/27 17:59
 * description：
 */

public class MuliteActivity extends AppCompatActivity implements LeftAdapter.OnItemClickListener {
	private RecyclerView leftRecyclerView;
	private RecyclerView centerRecyclerView;

	private List<MulitBean> list;

	private CenterAdapter centerAdapter;
	private LeftAdapter leftAdapter;

	private LeftAdapter.LeftViewHolder lastViewHolder;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mulit);
		leftRecyclerView = findViewById(R.id.left_recy);
		centerRecyclerView = findViewById(R.id.center_recy);
		leftRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 5, Color.BLACK));
		leftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		centerRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 10, Color.RED));
		centerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		list = new ArrayList<>();
		leftAdapter = new LeftAdapter(list, this);
		leftRecyclerView.setAdapter(leftAdapter);
		centerAdapter = new CenterAdapter(R.layout.item_center);
		centerRecyclerView.setAdapter(centerAdapter);
		loadData();
	}

	private void loadData() {

		for (int i = 0; i < 20; i++) {
			MulitBean mulitBean = new MulitBean();
			mulitBean.setTitle(getName());
			List<String> child = new ArrayList<>();
			for (int j = 0; j < 10; j++) {
				child.add(getName());
			}
			mulitBean.setChild(child);
			list.add(mulitBean);
		}
		//刷新数据
		leftAdapter.notifyDataSetChanged();
		//要等view加载完成再获取  防止空指针
		leftRecyclerView.post(new Runnable() {
			@Override
			public void run() {
				//只能在第一次调用 并且没有主动对view造成滑动  注意类型转换异常
				LeftAdapter.LeftViewHolder childViewHolder = (LeftAdapter.LeftViewHolder) leftRecyclerView.getChildViewHolder(leftRecyclerView.getChildAt(0));
				childViewHolder.setVis();
				lastViewHolder = childViewHolder;
				centerAdapter.refresh(list.get(0).getChild());
			}
		});

	}

	String getName() {
		StringBuilder stringBuilder = new StringBuilder();
		//用于生成随机字符串
		String str = "田晓龙张超张硕李文茜陈瑶瑶";
		int length = str.length();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			int i1 = random.nextInt(length);
			stringBuilder.append(str.charAt(i1));
		}
		return stringBuilder.toString();
	}


	@Override
	public void onItemClick(LeftAdapter.LeftViewHolder viewHolder, View view, int position) {
		if (lastViewHolder != null) {
			lastViewHolder.itemView.setTag(null);
			lastViewHolder.setGone();
		}
		viewHolder.setVis();
		viewHolder.itemView.setTag(position);
		lastViewHolder = viewHolder;
		centerAdapter.refresh(list.get(position).getChild());
	}


}
