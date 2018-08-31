package com.location.mvp.mvp_route_demo.base;

import com.location.mvp.mvproutelibrary.error.IResponseErrorMsg;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 13:29
 * description：
 */

public class ErrorResponse implements IResponseErrorMsg {

	@Override
	public String getErrorMsg(int errcode) {
		String msg = "";
		switch(errcode){
			case -1:
				msg=  "好像账号不对啊";
				default:
		}
		return msg;
	}
}
