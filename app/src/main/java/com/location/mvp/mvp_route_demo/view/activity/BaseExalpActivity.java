package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.BundleUtils;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduler;

import java.util.ArrayList;

/**
 * @author location
 * @Date 2018/11/19
 */
@Layout(R.layout.activity_base)
public class BaseExalpActivity extends BaseActivity implements View.OnClickListener {

	private ImageView imageView;


	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		imageView = findViewById(R.id.base_image);
		RxScheduler.click(this, imageView);
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
		switch (v.getId()) {
			case R.id.base_image:
				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setEmail("Dsadsadsa");
				Bundle bundle = new Bundle();
				BundleUtils.putObject(bundle,"test",loginResponse);
				ArrayList<String> list = new ArrayList<>();
				list.add("测试");
				list.add("看看");
				ArrayList<NoMessageBean> noMessageBeanArrayList = new ArrayList<>();
				noMessageBeanArrayList.add(new NoMessageBean("12",1));
				noMessageBeanArrayList.add(new NoMessageBean("13",1));
				bundle.putParcelableArrayList("nomessage", noMessageBeanArrayList);
				bundle.putStringArrayList("ni",list);
				Intent intent = new Intent(this, ImageActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			default:

		}
	}
}
