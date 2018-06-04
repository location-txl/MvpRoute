package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.widget.RadioGroup;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.contract.HomeContract;
import com.location.mvp.mvp_route_demo.presenter.HomePresenter;
import com.location.mvp.mvp_route_demo.view.fragment.FourFragment;
import com.location.mvp.mvp_route_demo.view.fragment.OneFragment;
import com.location.mvp.mvp_route_demo.view.fragment.ThreeFragment;
import com.location.mvp.mvp_route_demo.view.fragment.TwoFragment;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.TimeUtils;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 14:42
 * description：
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View, RadioGroup.OnCheckedChangeListener {
	private RadioGroup radioGroup;


	@Override
	public void onshowError(BaseThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_home;
	}

	@Override
	protected void initView() {
		radioGroup = findViewById(R.id.home_radio);
		radioGroup.setOnCheckedChangeListener(this);
		FragmentUtils.getInstance(this)
				.start(OneFragment.class)
				.add(R.id.home_fre)
				.commit();
		LogUtils.e(TimeUtils.hxDate(System.currentTimeMillis()));
		LogUtils.e(TAG,"ceshi");


	}





	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected HomePresenter createPresenter() {
		return null;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
			case R.id.home_1:
				FragmentUtils.getInstance(this)
						.start(OneFragment.class)
						.add(R.id.home_fre)
						.commit();
				break;
			case R.id.home_2:
				FragmentUtils.getInstance(this)
						.start(TwoFragment.class)
						.add(R.id.home_fre)
						.commit();
				break;
			case R.id.home_3:
				FragmentUtils.getInstance(this)
						.start(ThreeFragment.class)
						.add(R.id.home_fre)
						.commit();
				break;
			case R.id.home_4:
				FragmentUtils.getInstance(this)
						.start(FourFragment.class)
						.add(R.id.home_fre)
						.commit();
				break;
		}
	}
}
