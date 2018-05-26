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




    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
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
}
