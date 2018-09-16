package com.location.mvp.mvp_route_demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 19:48
 * description：
 */

public class ZcChildBean implements Parcelable {

	private String childTitle;
	private int id;

	private boolean isSelect;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public String getChildTitle() {
		return childTitle;
	}

	public void setChildTitle(String childTitle) {
		this.childTitle = childTitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZcChildBean() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.childTitle);
		dest.writeInt(this.id);
		dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
	}

	protected ZcChildBean(Parcel in) {
		this.childTitle = in.readString();
		this.id = in.readInt();
		this.isSelect = in.readByte() != 0;
	}

	public static final Creator<ZcChildBean> CREATOR = new Creator<ZcChildBean>() {
		@Override
		public ZcChildBean createFromParcel(Parcel source) {
			return new ZcChildBean(source);
		}

		@Override
		public ZcChildBean[] newArray(int size) {
			return new ZcChildBean[size];
		}
	};
}
