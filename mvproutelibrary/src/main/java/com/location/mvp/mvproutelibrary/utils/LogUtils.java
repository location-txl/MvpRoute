/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.utils;

import android.text.TextUtils;
import android.util.Log;



public class LogUtils {
    private static final int LIVE_V = Log.VERBOSE;
    private static final int LIVE_D = Log.DEBUG;
    private static final int LIVE_I = Log.INFO;
    private static final int LIVE_W = Log.WARN;
    private static final int LIVE_E = Log.ERROR;


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
        if (isPrintLine) Log.println(live, tag, "--------------------------------------------");
        if (isPrintClassName) Log.println(live, tag, callMethodAndLine());
        Log.println(live, tag, message);
        if (isPrintLine) Log.println(live, tag, "--------------------------------------------");
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
        String result = "at .";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
//        result += thisMethodStack.getClassName().lastIndexOf(".",1) + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    public static class LogUtilsBuilder {
        public LogUtilsBuilder setisLogcat(boolean isLocat) {
            LogUtils.islogcat = isLocat;
            return this;
        }

        public LogUtilsBuilder setPrintLine(boolean isLine) {
            LogUtils.isPrintLine = isLine;
            return this;
        }

        public LogUtilsBuilder setPrintClass(boolean isclass) {
            LogUtils.isPrintClassName = isclass;
            return this;
        }

    }
}
