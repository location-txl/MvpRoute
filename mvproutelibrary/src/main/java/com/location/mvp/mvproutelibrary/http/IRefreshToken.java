package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.IBaseBean;

import io.reactivex.Observable;


/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 12:21
 * description：
 */

public interface IRefreshToken {

	/**
	 * 执行刷新token
	 * @return
	 */
	Observable refreshTokenSuccful();

	/**
	 * 是否是token过期异常
	 * @param code
	 * @param errorMsg
	 * @return
	 */
	boolean isTokenException(int code,String errorMsg);
}
