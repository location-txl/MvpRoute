/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;


/**
 * 用于管理头尾布局
 */
public class DataBean {
	private @Nullable
	Object response;
	private @LayoutRes
	int layout;


	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public DataBean(Object response, @LayoutRes int layout) {
		this.response = response;
		this.layout = layout;
	}

	public DataBean(int layout) {
		this.layout = layout;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataBean) {
			if (((DataBean) obj).layout == layout && response.equals(((DataBean) obj).response)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "DataBean{" +
				"response=" + response +
				", layout=" + layout +
				'}';
	}
}
