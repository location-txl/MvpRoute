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

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;


/**
 * 二次封装的RecyclerView适配器
 *
 * @param <T> 适配器数据类型
 * @param <V> 自定义的viewholder 当然也可以使用默认的 #BaseViewHolder
 */

public abstract class BaseAdapter<T, V extends BaseViewHolder> extends AbstractBaseAdapter<T, V> implements AdapterList.ChangeListener<DataBean> {

	public BaseAdapter(int layout) {
		super(layout);
	}

	public BaseAdapter(Collection data, int layout) {
		super(data, layout);
	}

	public BaseAdapter(Collection data, int layout, OnItemClickListener<V> listener) {
		super(data, layout);
		this.listener = listener;
	}

	private OnItemClickListener<V> listener;

	/**
	 * view的点击事件
	 *
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener<V> listener) {
		this.listener = listener;
	}

	@Override
	protected void registerListener(V holder) {
		holder.registerItemListener(listener, getHeaderCount());
	}


}
