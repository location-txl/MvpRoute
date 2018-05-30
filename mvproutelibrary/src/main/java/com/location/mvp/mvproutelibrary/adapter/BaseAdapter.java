package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:  Recycler的基础适配器
 * 创建人: 田晓龙
 * 创建时间: 2018/5/25 0025 23:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> data;
    private View emptyView;
    private ArrayList<DataBean> headerList = new ArrayList<>();
    private ArrayList<DataBean> footerList = new ArrayList<>();


    private final int TYPE_EMPTY = -999999999;
    private final int TYPE_HEADER = 2000000000;
    private final int TYPE_FOOTER = 1000000000;
    private final int TYPE_NOMAL = 0;
    /**
     * 存储子View的点击事件
     */
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

    public void setChildOnClickListener(@IdRes int ids, @NonNull OnChildListener listener) {
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
        if (isHeaderType(viewType) != -1) {
            view = LayoutInflater.from(parent.getContext()).inflate(isHeaderType
                    (viewType), parent, false);

            return new ViewHolder(view);
        }

        if (isFooterType(viewType) != -1) {
            view = LayoutInflater.from(parent.getContext()).inflate(isFooterType(viewType),
                    parent, false);
            return new ViewHolder(view);
        }
        if (viewType == TYPE_EMPTY) {
            return new ViewHolder(emptyView);
        }
        if (layouts.length > 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(layouts[0], parent, false);
        }
        ViewHolder holder = new ViewHolder(view, listener, listenerSparseArray);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderPos(position)) {
            onBindHeaderViewHolder(holder, headerList.get(position).getResponse(), headerList.get
                    (position).getLayout());
            return;
        }
        if (isFooterPos(position)) {
         onBindFooterViewHolder(holder,footerList.get(position-headerList.size()-data.size()).getResponse(),footerList.get
                 (position-headerList.size()-data.size()).getLayout());
        }
        if ((position - headerList.size()) > data.size() - 1) {
            conver(holder, null, getItemViewType((position)));
        } else {
            conver(holder, data.get((position - headerList.size())), getItemViewType((position)));
        }
    }


    public abstract void conver(ViewHolder holder, T data, int viewType);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, Object response, @LayoutRes int
            layout) {
    }
    public void onBindFooterViewHolder(ViewHolder viewHolder, Object response, @LayoutRes int
            layout) {
    }
    @CallSuper
    @Override
    public int getItemCount() {
        if (data.isEmpty() && data.size() == 0 && emptyView != null) {
            return 1;
        }
        return data.size() + headerList.size()+footerList.size();
    }

    public void addHeaderView(Object t, @LayoutRes int layout) {
        headerList.add(new DataBean<>(t, TYPE_HEADER, layout));
    }

    public void addFooterView(Object t, @LayoutRes int layout) {
        footerList.add(new DataBean(t, TYPE_FOOTER, layout));
    }

    /**
     * 当没有数据时会显示此View
     *
     * @param view
     */
    public void setEmptyView(@NonNull View view) {
        this.emptyView = view;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        this.emptyView.setLayoutParams(params);
    }

    @Override
    public final int getItemViewType(int position) {
        if (data.isEmpty() && data.size() == 0 && emptyView != null) return TYPE_EMPTY;

        if (isHeaderPos(position)) {
            return headerList.get(position).getType();
        }
        if (isFooterPos(position)) {
            return footerList.get(position - data.size() - headerList.size()).getType();
        }
        return getItemType(position - headerList.size());
    }

    protected int getItemType(int position) {
        return TYPE_NOMAL;
    }

    public final void loadItem(Collection<T> items) {
        int size = data.size() + headerList.size();
        data.addAll(items);
        notifyItemRangeInserted(size, items.size());
        compatibilityDataSizeChanged(items.size());
    }


    public final void loadItem(T t) {
        data.add(t);
        notifyItemInserted(data.size() + headerList.size());
        compatibilityDataSizeChanged(1);
    }


    public final void refresh() {
        notifyDataSetChanged();
    }

    public final void refresh(Collection<T> response) {
        data.clear();
        data.addAll(response);
        notifyDataSetChanged();
    }

    public final void remove(@IntRange(from = 0) int position) {
        if (position >= data.size()) return;
        int size = data.size();
        data.remove(position);
        notifyItemRemoved(position + headerList.size());
        compatibilityDataSizeChanged(0);
    }


    private void compatibilityDataSizeChanged(int newDataSize) {
        int size = data.isEmpty() ? 0 : data.size();
        if (size == newDataSize) notifyDataSetChanged();
    }

    public final void clear() {
        data.clear();
        notifyDataSetChanged();
    }


    private boolean isHeaderPos(int position) {
        return getHeaderCount() > position;
    }

    private boolean isFooterPos(int position) {
        return position >= headerList.size() + data.size();
    }

    private int getHeaderCount() {
        return headerList.size();
    }

    private int getFooterCount() {
        return footerList.size();
    }

    private int isHeaderType(int type) {
        for (DataBean dataBean : headerList) {
            if (dataBean.getType() == type) {
                return dataBean.getLayout();
            }
        }
        return -1;
    }

    private int isFooterType(int type) {
        for (DataBean dataBean : footerList) {
            if (dataBean.getType() == type) {
                return dataBean.getLayout();
            }

        }
        return -1;
    }

}
