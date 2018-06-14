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

import com.quzubuluo.quzu.utils.GlideUtils;

/**
 * 项目名称: 趣租部落
 * 类描述:  基础viewholder类  类中方法自行扩展
 * 创建人: 田晓龙
 * 创建时间: 2018/5/25 0025 23:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public final class ViewHolder extends RecyclerView.ViewHolder {
	private View itemView;
	private SparseArray<View> viewCache;

	public ViewHolder(View itemView, final AbsListView.OnItemClickListener listener, SparseArray<OnChildClickListener> sparseArray, final int headerSize) {
		this(itemView);
		if (listener != null) {
			this.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(null, v, getAdapterPosition() - headerSize, -1);
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
							onChildClickListener.onChildClcik(ViewHolder.this, childView, getAdapterPosition() - headerSize);
						}
					});
				}
			}
		}
	}

	public ViewHolder(View itemView, OnHeaderClickListener listener,BaseAdapter baseAdapter) {
		this(itemView);

	}

	public ViewHolder(View itemView) {
		super(itemView);
		this.itemView = itemView;
		viewCache = new SparseArray<>();
	}

	public <T extends View> T findViewById(@IdRes int ids) {
		View view = viewCache.get(ids);
		if (view == null) {
			view = itemView.findViewById(ids);
			viewCache.put(ids, view);
		}
		return (T) view;
	}
public void registListener(final OnHeaderClickListener onHeaderClickListener, final Object data, final int position,final boolean isHeader){
itemView.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		onHeaderClickListener.onHeaderClick(itemView,data,position,isHeader);
	}
});
}
	public void setText(@IdRes int ids, CharSequence charSequence) {
		View view = findViewById(ids);
		if (view instanceof TextView) {
			((TextView) view).setText(charSequence);
		}
	}

	public View getItemView() {
		return itemView;
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

	public void setImageurl(@IdRes int ids, String url) {
		View view = findViewById(ids);
		if (view instanceof ImageView) {
			GlideUtils.loadImage(view.getContext(), url, (ImageView) view);
		}
	}

}
