package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/20 17:30
 * description：
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		findViewById(R.id.id_1).setOnClickListener(this);
		findViewById(R.id.id_2).setOnClickListener(this);
		findViewById(R.id.id_3).setOnClickListener(this);
		findViewById(R.id.id_4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
//		FragmentUtils instance = FragmentUtils.getInstance(this);
		switch (v.getId()){
			case R.id.id_1:

				break;
			case R.id.id_2:
				break;
			case R.id.id_3:
				break;
			case R.id.id_4:
				break;
				default:
		}


	}
}
