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

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.util.List;


/**
 * item默认的viewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
	private View itemView;
	private SparseArray<View> viewCache;

	private Context context;

	public Context getContext() {
		return context;
	}




	public void registItemListener(final OnItemClickListener listener, SparseArray<OnChildClickListener> sparseArray, final int headerSize){
		if (listener != null) {
			this.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(BaseViewHolder.this, v, getAdapterPosition() - headerSize);
				}
			});
		}



		if (sparseArray != null && sparseArray.size() > 0) {
			int length = sparseArray.size();
			for (int i = 0; i < length; i++) {
				int ids = sparseArray.keyAt(i);
				final OnChildClickListener onChildClickListener = sparseArray.valueAt(i);
				final View childView = this.itemView.findViewById(ids);
				if (childView != null && onChildClickListener != null) {
					childView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onChildClickListener.onChildClcik(BaseViewHolder.this, childView, getAdapterPosition() - headerSize);
						}
					});
				}
			}
		}

	}

	public BaseViewHolder(View itemView) {
		super(itemView);
		this.itemView = itemView;
		viewCache = new SparseArray<>();
		this.context = this.itemView.getContext();
	}

	public <T extends View> T findViewById(@IdRes int ids) {
		View view = viewCache.get(ids);
		if (view == null) {
			view = itemView.findViewById(ids);
			viewCache.put(ids, view);
		}
		return (T) view;
	}

	public void registListener(final int layout, final OnHeaderClickListener onHeaderClickListener, final Object data, final int position, final boolean isHeader) {
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i("response===>" + data);
				onHeaderClickListener.onHeaderClick(layout, itemView, data, position, isHeader);
			}
		});
	}



	public View getItemView() {
		return itemView;
	}

	/**
	 * 设置文本
	 * @param ids
	 * @param charSequence
	 */
	public void setText(@IdRes int ids, CharSequence charSequence) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(charSequence);
		}
	}

	/**
	 * 设置文本
	 * @param ids
	 * @param message
	 */
	public void setText(@IdRes int ids, String message) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(message);
		}
	}


	/**
	 * 设置文本
	 * @param ids
	 * @param stringres
	 */
	public void setText(@IdRes int ids, @StringRes int stringres) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(stringres);
		}
	}

	/**
	 * 设置Imageview资源图片
	 * @param ids
	 * @param resouce
	 */
	public void setImageResouce(@IdRes int ids, @DrawableRes int resouce) {
		View view = findViewById(ids);
		if (view instanceof ImageView) {
			((ImageView) view).setImageResource(resouce);
		}
	}

	/**
	 * 设置显示某个view
	 * @param ids
	 */
	public void setVisibility(@IdRes int ids) {
		View view = findViewById(ids);
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏某个view
	 * @param ids
	 */
	public void setGone(@IdRes int ids) {
		View view = findViewById(ids);
		if (view != null) {
			view.setVisibility(View.GONE);
		}
	}




}
