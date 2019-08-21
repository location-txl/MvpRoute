package com.location.mvp.mvproutelibrary.adapter;

import android.os.Bundle;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tianxiaolong
 * time：19-4-28 下午10:16
 * description：
 * 选择适配器 支持单选和多选
 */
public abstract class SelectAdapter<T, V extends BaseViewHolder> extends AbstractBaseAdapter<T, V> {
    public static final int TYPE_SINGLE = 0x001;
    public static final int TYPE_MULTIPLE = 0x002;

    private static final String EXERA_STATE = "SelectAdapter-state";
    private static final int DEFAULT_POSITION = -1;

    public SelectAdapter(int layout) {
        super(layout);
    }

    private int selectType;

    private List<T> selectList;

    public SelectAdapter(Collection<T> data, int layout, @SelectMode int selectType) {
        super(data, layout);
        this.selectType = selectType;
        selectPosition = new ArrayList<>();
    }

    public SelectAdapter(Collection<T> data, int layout) {
        this(data, layout, TYPE_SINGLE);
    }

    private int lastPosition = DEFAULT_POSITION;

    private List<Integer> selectPosition;


    @Override
    protected void registerListener(V holder) {
        if (holder.getItemView() == null) {
            return;
        }
        holder.getItemView().setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            selectItem(adapterPosition);
        });
    }


    private void selectItem(int selectPosition) {
        if (selectPosition == lastPosition) {
            return;
        }
        if (selectType == TYPE_SINGLE && lastPosition != DEFAULT_POSITION) {
            notifyPositionState(false,lastPosition);
            Bundle lastBundle = new Bundle();
            lastBundle.putBoolean(EXERA_STATE, false);
            notifyItemChanged(lastPosition, lastBundle);
        }

        boolean state = false;
        if(selectType==TYPE_SINGLE){
            lastPosition = selectPosition;
            this.selectPosition.clear();
            this.selectPosition.add(selectPosition);
            state = true;
        }else if(selectType == TYPE_MULTIPLE){
             state = this.selectPosition.contains(selectPosition);
             if(state){
                 this.selectPosition.remove(Integer.valueOf(selectPosition));
             }else {
                 this.selectPosition.add(selectPosition);
             }
             state = !state;
        }
            notifyPositionState(state, selectPosition);

    }

    private void notifyPositionState(boolean state,int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXERA_STATE,state);
        notifyItemChanged(position, bundle);
    }

    @Override
    public void onBindViewHolder(V holder, int position, List<Object> payloads) {
        if (payloads != null && !payloads.isEmpty()) {
            Object o = payloads.get(0);
            if (o instanceof Bundle) {
                Bundle bundle = (Bundle) o;
                boolean state = bundle.getBoolean(EXERA_STATE, false);
                onCheckChange(holder, getData(position - getHeaderCount()), getItemViewType(position), state);
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }


    @Override
    public void conver(V holder, @Nullable T data, int viewType) {
        onConverData(holder, data, viewType);
        boolean state = selectPosition.contains(holder.getAdapterPosition());
        onCheckChange(holder, data, viewType, state);
    }

    /**
     * if getSelectPosition is  {@link #DEFAULT_POSITION}  there are no selected items
     *
     * @return select item position
     * @throws RuntimeException when mode is {@link #TYPE_MULTIPLE}
     */
    public final int getCurrentPosition() {
        if (selectType == TYPE_MULTIPLE) {
            throw new RuntimeException("select mode is single mulitiple you need use getSelectItem method");
        }
        return lastPosition != DEFAULT_POSITION ? lastPosition - getHeaderCount() : lastPosition;
    }


    /**
     * get select state item
     * @return select item
     * @throws RuntimeException() when mode is {@link #TYPE_MULTIPLE}
     */
    public T getCurrentItem() {
        if (selectType == TYPE_MULTIPLE) {
            throw new RuntimeException("select mode is single mulitiple you need use getSelectItem method");
        }
        if (selectList.isEmpty()) {
            return null;
        }
        return selectList.get(0);
    }

    public List<T> getSelectItem() {
        return selectList;
    }

    public List<Integer> getSelectPosition(){
        return selectPosition;
    }

    public boolean isSelect() {
        return lastPosition != DEFAULT_POSITION;
    }

    public void setSelectItem(int position) {
        selectItem(position + getHeaderCount());
    }


    protected abstract void onCheckChange(V holder, T data, int viewType, boolean checked);


    protected abstract void onConverData(V holder, @Nullable T data, int viewType);


    @IntDef({TYPE_MULTIPLE, TYPE_SINGLE})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface SelectMode {

    }
}
