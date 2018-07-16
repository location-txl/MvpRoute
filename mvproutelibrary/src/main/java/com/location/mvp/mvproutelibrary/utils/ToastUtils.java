package com.location.mvp.mvproutelibrary.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.location.mvp.mvproutelibrary.R;

/**
 * 项目:Mvp_Route_Demo
 * author：location
 * time：2018/5/31 15:18
 * description：
 */

public class ToastUtils {
	private static Context context;
	private static Toast toast;
	private static final String error = "you need initialize ToastUtils in Application ";

	public static void init(Context context) {
		ToastUtils.context = context;
	}

	public static void showShort(String message) {
		show(message,Toast.LENGTH_SHORT);
	}

	private static void show(String message, int duration) {
		if (init(message,duration)){
			toast.setText(message);
			toast.setDuration(duration);
			toast.show();
		}
	}

	public static void showShort(@StringRes int stringid) {
		if(context==null)	throw new NullPointerException(error);
		show(context.getResources().getString(stringid),Toast.LENGTH_SHORT);
	}

	public static  void showLong(String message){
		show(message,Toast.LENGTH_LONG);

	}
	public static  void showLong(@StringRes int stringid){
		if(context==null)	throw new NullPointerException(error);
		show(context.getResources().getString(stringid),Toast.LENGTH_LONG);

	}
	private static boolean init(String message,int duration) {
		if (context == null) {
			throw new NullPointerException(error);
		}
		if (toast == null) {
			toast = Toast.makeText(context, message, duration);
			return false;
		}
		return true;
	}
}
