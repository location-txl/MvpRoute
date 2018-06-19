package com.location.mvp.mvp_route_demo.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.utils.DividerGridItemDecoration;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 17:23
 * description：
 */

public class GroupActivity extends AppCompatActivity {
	RecyclerView recyclerView;
	List<GroupBean> list;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);
		findViewById(R.id.id_lin).setVisibility(View.GONE);
		recyclerView = findViewById(R.id.recy);
		list = new ArrayList<>();
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		String[] stringArray = getResources().getStringArray(R.array.group_arrays);
		//设置不同的组的id
		for (int i = 0; i < stringArray.length; i++) {

			String message = stringArray[i];
			GroupBean bean = new GroupBean(message);
			if (i < 3) {
				bean.setGroupId(1);
			}
			if (i == 3 || i == 4) {
				bean.setGroupId(2);
			}
			if (i == 5) {
				bean.setGroupId(3);
			}
			list.add(bean);
		}
		//设置普通分割线
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 1, Color.parseColor("#00000000"));
		//设置分组分割线
		dividerItemDecoration.setGroupDivider(20, Color.parseColor("#F0F0F0"));
		//添加分割线
		recyclerView.addItemDecoration(dividerItemDecoration);
		//适配器
		GroupAdapter adapter = new GroupAdapter(list, R.layout.item_type);
		recyclerView.setAdapter(adapter);
	}
}
