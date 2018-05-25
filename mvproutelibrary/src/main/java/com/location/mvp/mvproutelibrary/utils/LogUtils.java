package com.location.mvp.mvproutelibrary.utils;

import android.util.Log;

import java.security.KeyStore;

/**
 * 项目:logUtils
 * author：田晓龙
 * time：2018/5/25 17:12
 * description：
 */

public class LogUtils {
	public static final int LIVE_V = 1;
	public static final int LIVE_D = 2;
	public static final int LIVE_I = 3;
	public static final int LIVE_W = 4;
	public static final int LIVE_E = 5;


	private static boolean islogcat = true;

	private static boolean isPrintClassName;

	public static void e(String tag, String message) {
		logHead(tag, LIVE_E);
		Log.e(tag, message);
	}

	public static void e(String message) {
		e(getClassName(), message);
	}


	private static void logHead(String tag, int live) {
		switch (live) {
			case LIVE_V:
				Log.v(tag, "------------------------------------------");
				Log.v(tag, callMethodAndLine());
				break;
			case LIVE_D:
				Log.d(tag, "------------------------------------------");
				Log.d(tag, callMethodAndLine());
				break;
			case LIVE_I:
				Log.i(tag, "------------------------------------------");
				Log.i(tag, callMethodAndLine());
				break;
			case LIVE_W:
				Log.w(tag, "------------------------------------------");
				Log.w(tag, callMethodAndLine());
				break;
			case LIVE_E:
				Log.e(tag, "------------------------------------------");
				Log.e(tag, callMethodAndLine());
				break;
		}
	}


	private static String getClassName() {
		String result;
		StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
		result = thisMethodStack.getClassName();
		int lastIndex = result.lastIndexOf(".");
		result = result.substring(lastIndex + 1, result.length());
		return result;
	}

	private void ss() {
	}

	private static String callMethodAndLine() {
		String result = "at ";
		StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[4];
		result += thisMethodStack.getClassName() + ".";
		result += thisMethodStack.getMethodName();
		result += "(" + thisMethodStack.getFileName();
		result += ":" + thisMethodStack.getLineNumber() + ")  ";
		return result;
	}
}
