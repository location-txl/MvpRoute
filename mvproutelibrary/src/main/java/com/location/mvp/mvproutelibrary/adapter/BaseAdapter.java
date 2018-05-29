package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.location.mvp.mvproutelibrary.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/25 0025 23:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
	protected List<T> data;

	private SparseArray<OnChildListener> listenerSparseArray;

	protected @LayoutRes
	int[] layouts;

	public BaseAdapter(int[] layouts) {
		this.layouts = layouts;
		data = new ArrayList<>();
	}

	public BaseAdapter(Collection<T> data, int[] layouts) {
		this.data = new ArrayList<>(data);
		this.layouts = layouts;
	}

	public BaseAdapter(int layout) {
		this.layouts = new int[]{layout};
		data = new ArrayList<>();
	}

	public BaseAdapter(Collection<T> data, int layout) {
		this.data = new ArrayList<>(data);
		this.layouts = new int[]{layout};
	}


	/**
	 * 添加字view的点击事件
	 *
	 * @param ids
	 * @param listener
	 */
	public void setChildOnClickListener(@IdRes int ids, OnChildListener listener) {
		if (listenerSparseArray == null) {
			listenerSparseArray = new SparseArray<>();
		}
		listenerSparseArray.put(ids, listener);
	}

	public BaseAdapter(Collection<T> data, int[] layouts, AbsListView.OnItemClickListener listener) {
		this.data = new ArrayList<>(data);
		this.layouts = layouts;
		this.listener = listener;
	}

	public BaseAdapter(Collection<T> data, int layout, AbsListView.OnItemClickListener listener) {
		this.data = new ArrayList<>(data);
		this.layouts = new int[]{layout};
		this.listener = listener;
	}

	protected AbsListView.OnItemClickListener listener;

	public void setListener(AbsListView.OnItemClickListener listener) {
		this.listener = listener;
	}

	public void setData(Collection<T> data) {
		this.data = new ArrayList<>(data);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if (layouts.length > 1) {
			view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(layouts[0], parent, false);
		}
		ViewHolder holder = new ViewHolder(view, listener,listenerSparseArray);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (position > data.size() - 1) {
			conver(holder, null, getItemViewType(position));
		} else {
			conver(holder, data.get(position), getItemViewType(position));
		}
	}

	public abstract void conver(ViewHolder holder, T data, int viewType);

	@Override
	public int getItemCount() {
		return data.size();
	}


}
