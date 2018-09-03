package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/3 20:48
 * description：
 */

public class SpActivity extends BaseToActivity {
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "SpUtils";
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_sp;
	}


	@Override
	protected void initView() {
		super.initView();
		findViewById(R.id.sp_test)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						List<LoginResponse> list = new ArrayList<>();
						for (int i=0;i<3;i++){
							LoginResponse response = new LoginResponse();
							response.setId(i);
							response.setEmail("ceshi"+i);
							list.add(response);
						}
						SpUtils.getInstance().putValue("test",list);
					}
				});
		findViewById(R.id.sp_select)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						List<LoginResponse> object = SpUtils.getInstance().getObject("test",List.class);
						ToastUtils.showShort(object.toString());
					}
				});

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
