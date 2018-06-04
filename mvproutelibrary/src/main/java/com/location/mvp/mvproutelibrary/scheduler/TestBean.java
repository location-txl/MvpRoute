package com.location.mvp.mvproutelibrary.scheduler;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/4 11:49
 * description：
 */

public class TestBean {

	/**
	 * code : 201
	 * message : 成功
	 * data : {"userId":123,"userName":"田晓龙","sex":"男","isDuck":false}
	 */

	private int code;
	private String message;
	private DataBean data;

	@Override
	public String toString() {
		return "TestBean{" +
				"code=" + code +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public static class DataBean {
		@Override
		public String toString() {
			return "DataBean{" +
					"userId=" + userId +
					", userName='" + userName + '\'' +
					", sex='" + sex + '\'' +
					", isDuck=" + isDuck +
					'}';
		}

		/**
		 * userId : 123
		 * userName : 田晓龙
		 * sex : 男
		 * isDuck : false
		 */

		private int userId;
		private String userName;
		private String sex;
		private boolean isDuck;

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public boolean isIsDuck() {
			return isDuck;
		}

		public void setIsDuck(boolean isDuck) {
			this.isDuck = isDuck;
		}
	}
}
