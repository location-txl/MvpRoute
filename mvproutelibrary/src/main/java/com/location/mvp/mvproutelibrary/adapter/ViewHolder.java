package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import com.location.mvp.mvproutelibrary.R;

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
			for (int i = 0; i < sparseArray.size(); i++) {
				OnChildListener onChildListener = sparseArray.get(i);
				int i1 = sparseArray.keyAt(i);
			}
		}
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
}
