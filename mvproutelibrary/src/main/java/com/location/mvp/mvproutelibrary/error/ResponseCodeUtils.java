package com.location.mvp.mvproutelibrary.error;

/**
 * @author location
 */
public class ResponseCodeUtils {

	public static String getMsg(int code) {
		String msg = "";
		switch (code) {
			default:
				msg = "未知错误";
		}
		return msg;
	}
}
