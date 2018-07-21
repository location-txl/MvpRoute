package com.location.mvp.mvproutelibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 项目:logUtils
 * author：location
 * time：2018/5/25 17:12
 * description：
 */

public class LogUtils {
    public static final int LIVE_V = Log.VERBOSE;
    public static final int LIVE_D = Log.DEBUG;
    public static final int LIVE_I = Log.INFO;
    public static final int LIVE_W = Log.WARN;
    public static final int LIVE_E = Log.ERROR;


    private static boolean islogcat = true;

    private static boolean isPrintClassName = true;

    private static boolean isPrintLine = true;

    public static void e(String tag, String message) {
        log(LIVE_E, tag, message);
    }

    public static void e(String message) {
        log(LIVE_E, null, message);
    }

    public static void v(String tag, String message) {
        log(LIVE_V, tag, message);
    }

    public static void v(String message) {
        log(LIVE_V, null, message);
    }

    public static void d(String tag, String message) {
        log(LIVE_D, tag, message);
    }

    public static void d(String message) {
        log(LIVE_D, null, message);
    }

    public static void i(String tag, String message) {
        log(LIVE_I, tag, message);
    }

    public static void i(String message) {
        log(LIVE_I, null, message);
    }

    public static void w(String tag, String message) {
        log(LIVE_W, tag, message);
    }

    public static void w(String message) {
        log(LIVE_W, null, message);
    }

    private static void log(int live, String tag, String message) {
        if (!islogcat) return;
        if (TextUtils.isEmpty(tag)) tag = getClassName();
//        if (isPrintLine) Log.println(live, tag, "--------------------------------------------");
//        if (isPrintClassName) Log.println(live, tag, callMethodAndLine());
        Log.println(live, tag, message);
//        if (isPrintLine) Log.println(live, tag, "--------------------------------------------");
    }


    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }


    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    public static class Builder {
        public Builder setisLogcat(boolean isLocat) {
            LogUtils.islogcat = isLocat;
            return this;
        }

        public Builder setPrintLine(boolean isLine) {
            LogUtils.isPrintLine = isLine;
            return this;
        }

        public Builder setPrintClass(boolean isclass) {
            LogUtils.isPrintClassName = isclass;
            return this;
        }

    }
}
