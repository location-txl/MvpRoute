package com.location.mvp.mvp_route_demo.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.modle.bean.DataBean;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.OnChildClickListener;
import com.location.mvp.mvproutelibrary.adapter.OnHeaderClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.DividerGridItemDecoration;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 16:12
 * description：
 */

public class RecyclerTypeActivity extends AppCompatActivity implements View.OnClickListener {
	RecyclerView recyclerView;
	private TypeAdapter typeAdapter;
	private List<TypeBean> list;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);
		findViewById(R.id.recy_add).setOnClickListener(this);
		findViewById(R.id.recy_remove).setOnClickListener(this);
		findViewById(R.id.recy_clear).setOnClickListener(this);
		findViewById(R.id.recy_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RecyclerTypeActivity.this,GroupActivity.class));
			}
		});
		recyclerView = findViewById(R.id.recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//设置分割线
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 2, Color.parseColor("#000000")));
		list = new ArrayList<>();
		//生成随机数据
		for (int i = 0; i < 50; i++) {
			Random random = new Random();
			int unit = random.nextInt(100);
			TypeBean bean = null;
			if (unit % 2 == 0) {
				bean = new TypeBean(TypeBean.TYPE_IMG, "123");
			} else {
				bean = new TypeBean(TypeBean.TYPE_TEXT, "我是第" + i + "个");
			}
			list.add(bean);
		}
		typeAdapter = new TypeAdapter(list, R.layout.item_type);
		//绑定多type类型数据
		typeAdapter.addType(TypeBean.TYPE_TEXT, R.layout.item_type)
				.addType(TypeBean.TYPE_IMG, R.layout.item_image);
		//设置头布局  头布局的属性设置  在adapter里面做
		typeAdapter.addHeaderView("我是文字头布局", R.layout.header_text_view);
		typeAdapter.addHeaderView("3213", R.layout.item_img);

		typeAdapter.addHeaderView("我是第二个头布局", R.layout.header_text_view);
		typeAdapter.addHeaderView("我是第三个个头布局", R.layout.header_text_view);
		//为false  则头部局  尾部局之间的分割线不绘制 默认绘制
		typeAdapter.setDrawHeaderFooterLine(false);
		//空布局显示模式 具体点击去有注释
		typeAdapter.setEmptyModle(BaseAdapter.ERMTY_MODLE_HEADERS);
		typeAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.item_empty_view, null));
		//设置item点击事件
		typeAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(RecyclerTypeActivity.this, "点击了第" + position + "项", Toast.LENGTH_SHORT).show();
			}
		});

		//设置item里面的子view点击事件
		typeAdapter.setOnChildClickListener(R.id.item_btn, new OnChildClickListener() {
			@Override
			public void onChildClcik(ViewHolder viewHolder, View view, int position) {
				Toast.makeText(RecyclerTypeActivity.this, "点击的是第" + position + "项里面的button", Toast.LENGTH_SHORT).show();
			}
		});
		/**
		 * 设置头尾布局的点击事件
		 * isHeader  true  则是头布局
		 * position  这里不是所对应的索引  而是当前headerview出现的次数
		 */
		typeAdapter.setOnHeaderClickListener(new OnHeaderClickListener() {

			@Override
			public void onHeaderClick(int layout, View view, @Nullable Object data, int position, boolean isHeader) {
				if (isHeader) {
					switch (layout) {
						case R.layout.header_text_view:
							Toast.makeText(RecyclerTypeActivity.this, "头布局是TextView position==" + position, Toast.LENGTH_SHORT).show();
							break;
						case R.layout.item_img:
							Toast.makeText(RecyclerTypeActivity.this, "头布局是图片", Toast.LENGTH_SHORT).show();
							break;
					}
				}
			}
		});
		//设置适配器
		recyclerView.setAdapter(typeAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//加载更多
			case R.id.recy_add:
				List<TypeBean> typeBeans = loadData(5);
				typeAdapter.loadItem(typeBeans);
				break;
				//清空
			case R.id.recy_clear:
				typeAdapter.clear();
				break;
				//刷新
			case R.id.recy_remove:
				typeAdapter.refresh(loadData(50));
				break;
		}
	}

	private List<TypeBean> loadData(int count) {
		List<TypeBean> data = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Random random = new Random();
			int unit = random.nextInt(100);
			TypeBean bean = null;
			if (unit % 2 == 0) {
				bean = new TypeBean(TypeBean.TYPE_IMG, "123");
			} else {
				bean = new TypeBean(TypeBean.TYPE_TEXT, "我是第" + i + "个");
			}
			data.add(bean);
		}
		return data;
	}
}
