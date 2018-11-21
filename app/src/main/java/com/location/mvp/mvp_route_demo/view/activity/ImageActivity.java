package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.RouteField;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.JsonUtils;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author location
 * @Date 2018/11/19
 */
@Layout(R.layout.activity_base)
public class ImageActivity extends BaseActivity {
		@RouteField("ni")
	private ArrayList<String> mT ;

	//	@BundField("test")
	private LoginResponse loginResponse;

	@RouteField("nomessage")
	private List<NoMessageBean> noMessageBeanList;

	//	@BundField
	private float fks;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		Bundle extras = getIntent().getExtras();
		LoginResponse test = JsonUtils.obtObject(extras.getString("test"), LoginResponse.class);
		ImageView imageView = findViewById(R.id.base_image);
//		ToastUtils.showShort(test.toString());
//          ToastUtils.showLong(loginResponse.toString());
//		LogUtils.d("mt","mt===>"+loginResponse.toString());
//		LogUtils.d("mt","float===>"+fks);
		if (noMessageBeanList != null) {

			LogUtils.d("mt", "nomessage===》" + noMessageBeanList.toString());
			LogUtils.d("mt", "nomessage===》mt===" + mT.toString());
		} else {
			LogUtils.d("mt", "nomessage===》----nulllll");
		}
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}
}
