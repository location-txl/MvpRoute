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

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * toast工具类
 */
public class ToastUtils {
	private static Context context;
	private static Toast toast;
	private static final String ERROR = "you need initialize ToastUtils in Application ";

	public static void init(Context context) {
		ToastUtils.context = context;
	}

	public static void showShort(String message) {
		show(message, Toast.LENGTH_SHORT);
	}

	private static void show(String message, int duration) {
		if (init(message, duration)) {
			toast.setText(message);
			toast.setDuration(duration);
			toast.show();
		}
	}

	public static void showShort(@StringRes int stringid) {
		if (context == null) throw new NullPointerException(ERROR);
		show(context.getResources().getString(stringid), Toast.LENGTH_SHORT);
	}

	public static void showLong(String message) {
		show(message, Toast.LENGTH_LONG);

	}

	public static void showLong(@StringRes int stringid) {
		if (context == null) throw new NullPointerException(ERROR);
		show(context.getResources().getString(stringid), Toast.LENGTH_LONG);

	}

	private static boolean init(String message, int duration) {
		if (context == null) {
			throw new NullPointerException(ERROR);
		}
		if (toast == null) {
			toast = Toast.makeText(context, message, duration);
			toast.show();
			return false;
		}
		return true;
	}
}
