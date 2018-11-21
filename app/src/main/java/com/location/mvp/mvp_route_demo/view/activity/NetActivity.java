package com.location.mvp.mvp_route_demo.view.activity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.presenter.NetPresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:39
 * description：
 */

public class NetActivity extends BaseToActivity<NetContract.Presenter> implements NetContract.View {

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {
		ToastUtils.showShort(baseThrowable.msg);
	}

	@Override
	protected String getTooBarTitle() {
		return "网络请求";
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_net;
	}

	@Override
	protected void loadData() {
		findViewById(R.id.net_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.loginWanAndroid("tianxiaolong", "tianxiaolong");
			}
		});
		findViewById(R.id.net_clean).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.cleanLogin();
			}
		});
		findViewById(R.id.net_collect).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = "loginUserName="+SpUtils.getInstance().getString(KeyUtils.USERNAME);
				String passwrold = "loginUserPassword=" + SpUtils.getInstance().getString(KeyUtils.PASSWORLD);

				for(int i=0;i<5;i++){
					presenter.getCollectList("0",username,passwrold);

				}
			}
		});
	}

	@NonNull
	@Override
	protected NetContract.Presenter createPresenter() {
		return new NetPresenter();
	}


	@Override
	public void loginSuccful(LoginResponse response) {
		SpUtils instance = SpUtils.getInstance();
		instance.putValue(KeyUtils.USERNAME, response.getUsername());
		instance.putValue(KeyUtils.PASSWORLD, response.getPassword());
		ToastUtils.showShort("登录成功");
	}

	@Override
	public void showCollectList(CollectListBean response) {
		ToastUtils.showShort("获取收藏列表成功");
	}

	@Override
	public void cleanLoginSuccful() {

		ToastUtils.showShort("清除登录状态成功");
	}

	@Override
	public int[] hideSoftByEditViewIds() {
		return super.hideSoftByEditViewIds();
	}
}
