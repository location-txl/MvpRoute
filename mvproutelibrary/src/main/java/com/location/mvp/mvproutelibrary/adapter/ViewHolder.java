package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/25 0025 23:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class ViewHolder extends RecyclerView.ViewHolder {
	public View itemView;

	public ViewHolder(View itemView, final AbsListView.OnItemClickListener listener, SparseArray<OnChildListener> sparseArray) {
		super(itemView);
		this.itemView = itemView;
		if (listener != null) {
			this.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(null, v, getAdapterPosition(), -1);
				}
			});
		}
		if (sparseArray != null && sparseArray.size() > 0) {
			int length = sparseArray.size();
			for (int i = 0; i < length; i++) {
				int ids = sparseArray.keyAt(i);
				final OnChildListener onChildListener = sparseArray.valueAt(i);
				final View childView = this.itemView.findViewById(ids);
				if (childView != null && onChildListener != null) {
					childView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onChildListener.onChildClcikListener(ViewHolder.this, childView, getAdapterPosition());
						}
					});
				}

			}
		}
	}

	public ViewHolder(View itemView) {
		super(itemView);
		this.itemView = itemView;
	}

	public <T extends View> T findViewById(@IdRes int ids) {
		return itemView.findViewById(ids);
	}

	public void setText(@IdRes int ids, CharSequence charSequence) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(charSequence);
		}
	}


	public void setText(@IdRes int ids, String message) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(message);
		}
	}


	public void setText(@IdRes int ids, @StringRes int stringres) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(stringres);
		}
	}

	public void setImageResouce(@IdRes int ids, @DrawableRes int resouce) {
		View view = findViewById(ids);
		if (view instanceof ImageView) {
			((ImageView) view).setImageResource(resouce);
		}
	}
}
