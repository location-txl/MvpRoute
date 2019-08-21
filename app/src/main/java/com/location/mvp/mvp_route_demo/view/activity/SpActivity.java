package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.bean.NoMessageBean;
import com.location.mvp.mvp_route_demo.contract.SpContract;
import com.location.mvp.mvp_route_demo.presenter.SpPresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduler;
import com.location.mvp.mvproutelibrary.utils.JsonUtils;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/3 20:48
 * description：
 */
@Layout(R.layout.activity_sp)
public class SpActivity extends BaseToActivity<SpContract.Presenter> implements View.OnClickListener, SpContract.View {
	private TextView hintText;
	private EditText key;
	private EditText value;
	private Button save, delete;
	private Button user,message,getUser,getMessage;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "SpUtils";
	}



	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		hintText = findViewById(R.id.sp_hint);
		key = findViewById(R.id.sp_key);
		value = findViewById(R.id.sp_value);
		save = findViewById(R.id.sp_save);
		delete = findViewById(R.id.sp_delete);
		save.setOnClickListener(this);
		delete.setOnClickListener(this);
		user = findViewById(R.id.sp_user);
		message = findViewById(R.id.sp_mesage);
		getUser = findViewById(R.id.sp_get_user);
		getMessage = findViewById(R.id.sp_get_message);
		RxScheduler.click(this,user);
		RxScheduler.click(this,message);
		RxScheduler.click(this,getUser);
		RxScheduler.click(this,getMessage);
	}

	@Override
	protected void loadData() {
		hintText.setText("请使用手机浏览器或者和手机在同一网络下的电脑打开");
		hintText.append("http://" + getIPAddress(this) + ":8080");
		hintText.append("网址来查看数据库");
	}


	public static String getIPAddress(Context context) {
		NetworkInfo info = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
				try {
					//Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
						 en.hasMoreElements(); ) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
							 enumIpAddr.hasMoreElements(); ) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}

			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
				return ipAddress;
			}
		} else {
		}
		return "获取失败";
	}

	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." +
				((ip >> 8) & 0xFF) + "." +
				((ip >> 16) & 0xFF) + "." +
				(ip >> 24 & 0xFF);
	}

	@NonNull
	@Override
	protected SpContract.Presenter createPresenter() {
		return new SpPresenter();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//保存
			case R.id.sp_save:
				presenter.save(key.getText().toString(), value.getText().toString());
				break;
			//删除
			case R.id.sp_delete:
				presenter.delete(key.getText().toString());
				break;
			case  R.id.sp_user:
				ToastUtils.showShort("user");
				List<LoginResponse> list = new ArrayList<>();
				for(int i=0;i<5;i++){
					LoginResponse loginResponse = new LoginResponse();
					loginResponse.setEmail("email"+i);
					loginResponse.setId(i);
					list.add(loginResponse);
				}
				String s = JsonUtils.obtJson(list);
				LogUtils.d("s===>" + s);
				List<LoginResponse> list2 = JsonUtils.obtArray(s, LoginResponse.class);
				LogUtils.d("s===>login===" + list2.toString());
//				SpUtils.getInstance().putValue(list);
				break;
			case  R.id.sp_mesage:
				List<NoMessageBean> list1 = new ArrayList<>();
				for(int i =0;i<5;i++){
					NoMessageBean noMessageBean = new NoMessageBean("123",i);
					list1.add(noMessageBean);
				}
				SpUtils.getInstance().putValue(list1);
				break;
			case  R.id.sp_get_user:
				break;
			case  R.id.sp_get_message:
				break;

			default:
		}
	}

	@Override
	public void showMessage(String message) {
		ToastUtils.showShort(message);
	}

}
