package com.location.mvp.mvproutelibrary.error;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * @author location
 */
public class ExceptionHandle {

	private static final int UNAUTHORIZED = 401;
	private static final int FORBIDDEN = 403;
	private static final int NOT_FOUND = 404;
	private static final int REQUEST_TIMEOUT = 408;
	private static final int INTERNAL_SERVER_ERROR = 500;
	private static final int BAD_GATEWAY = 502;
	private static final int SERVICE_UNAVAILABLE = 503;
	private static final int GATEWAY_TIMEOUT = 504;

	public static ResponeThrowable handleException(Throwable e) {
		ResponeThrowable ex;
		if (e instanceof HttpException) {
			HttpException httpException = (HttpException) e;
			ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
			switch (httpException.code()) {
				case UNAUTHORIZED:
				case FORBIDDEN:
				case NOT_FOUND:
				case REQUEST_TIMEOUT:
				case GATEWAY_TIMEOUT:
				case INTERNAL_SERVER_ERROR:
				case BAD_GATEWAY:
				case SERVICE_UNAVAILABLE:
				default:
					ex.msg = "网络错误";
					break;
			}
		} else if (e instanceof ServerException) {
			ServerException resultException = (ServerException) e;
			ex = new ResponeThrowable(resultException, resultException.result);
			ex.msg = resultException.msg;
		} else if (e instanceof ResponeThrowable) {
			ex = (ResponeThrowable) e;
		} else if (e instanceof JsonParseException
				|| e instanceof JSONException
				|| e instanceof ParseException) {
			ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
			ex.msg = "解析错误";
		} else if (e instanceof ConnectException) {
			ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
			ex.msg = "网络连接异常，请检查您的网络状态";
		} else if (e instanceof javax.net.ssl.SSLHandshakeException) {
			ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
			ex.msg = "证书验证失败";
		} else if (e instanceof ConnectTimeoutException) {
			ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
			ex.msg = "网络连接超时，请检查您的网络状态，稍后重试";
		} else if (e instanceof java.net.SocketTimeoutException) {
			ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
			ex.msg = "网络连接超时，请检查您的网络状态，稍后重试";
		} else if (e instanceof UnknownHostException) {
			ex = new ResponeThrowable(e, ERROR.UNKNOWN_HOST);
			ex.msg = "网络连接异常，请检查您的网络状态";
		} else {
			ex = new ResponeThrowable(e, ERROR.UNKNOWN);
			ex.msg ="未知错误" ;
		}
		return ex;
	}

	public static class ResponeThrowable extends Exception {

		public int result;
		public String msg;

		public ResponeThrowable(Throwable throwable, int result) {
			super(throwable);
			this.result = result;

		}
	}

	public static class ServerException extends RuntimeException {

		public int result;
		public String msg;

		public ServerException(int code, String msg) {
			this.result = code;
			this.msg = msg;
		}
	}

	/**
	 * 约定异常
	 */
	public class ERROR {

		/**
		 * 未知错误
		 */
		public static final int UNKNOWN = 1000;
		/**
		 * 解析错误
		 */
		public static final int PARSE_ERROR = 1001;
		/**
		 * 网络错误
		 */
		public static final int NETWORD_ERROR = 1002;
		/**
		 * 协议出错
		 */
		public static final int HTTP_ERROR = 1003;

		/**
		 * 证书出错
		 */
		public static final int SSL_ERROR = 1005;

		/**
		 * 连接超时
		 */
		public static final int TIMEOUT_ERROR = 1006;

		/**
		 * 服务器找不到
		 */
		public static final int UNKNOWN_HOST = 1007;
	}
}

