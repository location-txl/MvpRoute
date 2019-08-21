package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduler;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;
import com.location.mvp.mvproutelibrary.view.BobPopwindow;

/**
 * @author location
 * @Date 2018/11/21
 */
@Layout(R.layout.activity_bob)
public class BobActivity extends BaseActivity implements View.OnClickListener {

	private View bottomView;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		bottomView = findViewById(R.id.bob_bottom);
		RxScheduler.click(this, bottomView);
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	public void onClick(View v) {
		View view = LayoutInflater.from(this).inflate(R.layout.pop_test, null);
		new BobPopwindow.Builder(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
				.setAlpha(0.8f)
				.setDarken(this)
				.setViewClick(R.id.pop_clcik, new BobPopwindow.OnClickListener() {
					@Override
					public void onClick(BobPopwindow bobPopwindow, View veiw) {
						ToastUtils.showShort("点击了");
					}
				}).create().showBottom(this);
	}
}
