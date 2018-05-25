package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.location.mvp.mvp_route_demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/25 12:04
 * description：
 */

public class TestActivity extends AppCompatActivity {
	private List<Model> list;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_test);
		final Chart chart = findViewById(R.id.id_chart);
		chart.setDrawPoints(false).setFillArea(true).setPlayAnim(true);
		list = new ArrayList<>();
		initData();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				chart.setDatas(list);
			}
		},1000);



	}
	private void initData() {
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			Model model = new Model();
			model.percent =i;
			model.date = String.valueOf(i + 1);
			list.add(model);
		}
	}
}
