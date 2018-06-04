package com.location.mvp.mvproutelibrary.error;

/**
 * @author Kevin 服务器返回的错误码
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
