package com.location.mvp.mvp_route_demo.bean;

import java.util.List;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:33
 * description：
 */

public class LoginResponse  {

	/**
	 * collectIds : [1339,1494,1533,1260,1258,1255,96,94,203,2994,2922,-1,-1,-1,1165]
	 * email : tttx0307@163.com
	 * icon :
	 * id : 428
	 * password : tianxiaolong
	 * token :
	 * type : 0
	 * username : tianxiaolong
	 */

	private String email;
	private String icon;
	private int id;
	private String password;
	private String token;
	private int type;
	private String username;
	private List<Integer> collectIds;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Integer> getCollectIds() {
		return collectIds;
	}

	public void setCollectIds(List<Integer> collectIds) {
		this.collectIds = collectIds;
	}
}
