package com.location.mvp.mvproutelibrary.utils;

import android.util.Log;

/**
 * 项目:logUtils
 * author：田晓龙
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

    public static boolean isPrintLine = true;

    public static void setIslogcat(boolean islogcat) {
        LogUtils.islogcat = islogcat;
    }

    public static void setIsPrintClassName(boolean isPrintClassName) {
        LogUtils.isPrintClassName = isPrintClassName;
    }

    public static void setIsPrintLine(boolean isPrintLine) {
        LogUtils.isPrintLine = isPrintLine;
    }

    public static void e(String tag, String message) {
        if (isPrintLine) l(tag, LIVE_E);
        if (isPrintClassName) fild(tag, LIVE_E);
        Log.e(tag, message);
        if (isPrintLine) l(tag, LIVE_E);
    }

    public static void e(String message) {
        String tag = getClassName();
        if (isPrintLine) l(tag, LIVE_E);
        if (isPrintClassName) fild(tag, LIVE_E);
        Log.e(tag, message);
        if (isPrintLine) l(tag, LIVE_E);
    }


    public static void v(String tag, String message) {
        if (isPrintLine) l(tag, LIVE_V);
        if (isPrintClassName) fild(tag, LIVE_V);
        Log.v(tag, message);
        if (isPrintLine) l(tag, LIVE_V);
    }

    public static void v(String message) {
        String tag = getClassName();
        if (isPrintLine) l(tag, LIVE_V);
        if (isPrintClassName) fild(tag, LIVE_V);
        Log.v(tag, message);
        if (isPrintLine) l(tag, LIVE_V);
    }

    public static void d(String tag, String message) {
        if (isPrintLine) l(tag, LIVE_D);
        if (isPrintClassName) fild(tag, LIVE_D);
        Log.d(tag, message);
        if (isPrintLine) l(tag, LIVE_D);
    }


    private static void fild(String tag, int live) {
        switch (live) {
            case LIVE_V:
                Log.v(tag, callMethodAndLine());
                break;
            case LIVE_D:
                Log.d(tag, callMethodAndLine());
                break;
            case LIVE_I:
                Log.i(tag, callMethodAndLine());
                break;
            case LIVE_W:
                Log.w(tag, callMethodAndLine());
                break;
            case LIVE_E:
                Log.e(tag, callMethodAndLine());
                break;

        }

    }

    private static void l(String tag, int live) {
        switch (live) {
            case LIVE_V:
                Log.v(tag, "------------------------------------------");
                break;
            case LIVE_D:
                Log.d(tag, "------------------------------------------");
                break;
            case LIVE_I:
                Log.i(tag, "------------------------------------------");
                break;
            case LIVE_W:
                Log.w(tag, "------------------------------------------");
                break;
            case LIVE_E:
                Log.e(tag, "------------------------------------------");
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
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
