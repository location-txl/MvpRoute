package com.location.mvp.mvp_route_demo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.contract.PictureContract;
import com.location.mvp.mvp_route_demo.presenter.PicturePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseFragment;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/5 14:23
 * description：
 */

public class PictureFragment extends BaseFragment<PictureContract.Presenter> {
	//http://gank.io/api/data/%E7%A6%8F%E5%88%A9/30/1
	public static final String KEY_TYPE = "type";
	public static final int TYPE_LIN = 0x001;
	public static final int TYPE_Grild = 0x002;
	public static final int TYPE_STAT = 0x003;

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected void initView(View view) {

	}

	@Override
	protected void loadData() {

	}

	@Override
	protected void getBundle(Bundle bundle) {
		super.getBundle(bundle);
		int anInt = bundle.getInt(KEY_TYPE, -1);
	}

	@NonNull
	@Override
	protected PictureContract.Presenter createPresenter() {
		return new PicturePresenter();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_picture;
	}
}
