package com.location.mvp.mvp_route_demo.bean;

/**
 * @author tianxiaolong
 *         time：2019/1/11 23:17
 *         description：
 */

public class FoorData {
	private String fromName;
	private String toName;
	private String message;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FoorData(String fromName, String toName, String message) {
		this.fromName = fromName;
		this.toName = toName;
		this.message = message;
	}
}
