package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.contract.ResultContract;
import com.location.mvp.mvp_route_demo.presenter.ResultPresenter;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.Request;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;

import static com.location.mvp.mvproutelibrary.Base.Request.EXERA_REQUEST;
import static com.location.mvp.mvproutelibrary.Base.Request.EXERA_RESULT;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/7/31 16:02
 * description：
 */

public class ResultActivity extends BaseToActivity<ResultContract.Presenter> implements ResultContract.View, View.OnClickListener {
	private final int REQUESTCODE = 100;
	private TextView sinceText;
private TextView resulttext;
	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_result;
	}

	@Override
	protected String getTooBarTitle() {
		return "MvpRoute分派回传";
	}

	@Override
	protected void initView() {
		super.initView();
		resulttext = findViewById(R.id.resylt_message);
		findViewById(R.id.result_clcik).setOnClickListener(this);
		sinceText = findViewById(R.id.result_since);
		String sicne = getString(R.string.result_since);
		presenter.initSince(sicne);

	}

	@Override
	protected void loadData() {

	}


	@Request(request = REQUESTCODE)
	private void onResult(Intent intent) {
		resulttext.setText("回传成功\n调用onResult方法\n");
		resulttext.append("requestCode===>"+intent.getIntExtra(EXERA_REQUEST,-1));
		resulttext.append("\n");
		resulttext.append("resultCode===>" + intent.getIntExtra(EXERA_RESULT,-1));
		resulttext.append("\n");
		resulttext.append("intent====>"+intent.getExtras().toString());
	}

	@NonNull
	@Override
	protected ResultContract.Presenter createPresenter() {
		return new ResultPresenter();
	}

	@Override
	public void showMessage(String message) {

	}

	@Override
	public void showSince(CharSequence since) {
		sinceText.setText(since);
	}

	@Override
	public void onClick(View v) {
		presenter.startActivity(this, REQUESTCODE);
	}
}
