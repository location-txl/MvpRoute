package com.location.mvp.mvp_route_demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目:Mvp_Route_Demo
 *
 * @author：location time：2018/7/12 17:23
 * description：
 */

public class NoMessageBean implements Parcelable {

	private String name;
	private int id;

	@Override
	public String toString() {
		return "NoMessageBean{" +
				"name='" + name + '\'' +
				", id=" + id +
				'}';
	}

	public NoMessageBean(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeInt(this.id);
	}

	protected NoMessageBean(Parcel in) {
		this.name = in.readString();
		this.id = in.readInt();
	}

	public static final Parcelable.Creator<NoMessageBean> CREATOR = new Parcelable.Creator<NoMessageBean>() {
		@Override
		public NoMessageBean createFromParcel(Parcel source) {
			return new NoMessageBean(source);
		}

		@Override
		public NoMessageBean[] newArray(int size) {
			return new NoMessageBean[size];
		}
	};
}
