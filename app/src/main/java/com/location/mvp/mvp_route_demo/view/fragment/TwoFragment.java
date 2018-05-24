package com.location.mvp.mvp_route_demo.view.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.Base.BaseFragment;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseThrowable;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 14:50
 * description：
 */

public class TwoFragment extends BaseFragment {
	@Override
	public void onshowError(BaseThrowable baseThrowable) {

	}

	@Override
	protected void initView(View view) {
		TextView textView = view.findViewById(R.id.fragment_button);
		textView.setText("TWO");
	}

	@Override
	protected void loadData() {

	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_one;
	}
}
