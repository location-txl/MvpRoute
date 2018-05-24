package com.location.mvp.mvproutelibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 20:30
 * description：
 */

public class SpUtils {
	private static SpUtils spUtils;
	private static SharedPreferences sharedPreferences;
	private Map<String, SharedPreferences> sps = new HashMap<>();

	private SpUtils(Context context) {
		sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		sps.put(context.getPackageName(), sharedPreferences);
	}

	public static void init(Context context) {
		spUtils = new SpUtils(context);

	}

	public static void putValue(String key, String value) {
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.apply();
	}

	public static void putValue(String key, int value) {

	}

}
