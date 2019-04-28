package com.location.mvp.mvproutelibrary.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;


import java.util.Collection;
import java.util.List;

/**
 * @author tianxiaolong
 * time：19-4-28 下午10:16
 * description：
 * 单选适配器
 */
public abstract class SingleAdapter<T, V extends BaseViewHolder> extends AbstractBaseAdapter<T, V> {


	private static final String EXERA_STATE = "SingleAdapter-state";
	private static final int DEFAULT_POSITION = -1;
	public SingleAdapter(int layout) {
		super(layout);
	}



	public SingleAdapter(Collection<T> data, int layout) {
		super(data, layout);
	}

	private int lastPosition = DEFAULT_POSITION;

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
		if(selectPosition == lastPosition){
			return;
		}
		if(lastPosition != DEFAULT_POSITION){
			Bundle lastBundle = new Bundle();
			lastBundle.putBoolean(EXERA_STATE,false);
			notifyItemChanged(lastPosition,lastBundle);
		}
		lastPosition = selectPosition;
		Bundle bundle = new Bundle();
		bundle.putBoolean(EXERA_STATE, true);
		notifyItemChanged(lastPosition,bundle);
	}

	@Override
	public void onBindViewHolder(V holder, int position, List<Object> payloads) {
		 if(payloads!=null&&!payloads.isEmpty()){
			 Object o = payloads.get(0);
			  if(o instanceof Bundle){
			  	Bundle  bundle = (Bundle) o;
				  boolean state = bundle.getBoolean(EXERA_STATE, false);
				  onCheckChange(holder,getData(position-getHeaderCount()),getItemViewType(position),state);
			  }
		 }else{
			 super.onBindViewHolder(holder, position, payloads);
		 }
	}


	@Override
	public void conver(V holder, @Nullable T data, int viewType) {
		 onConverData(holder, data, viewType);
		 boolean state =  holder.getAdapterPosition()==lastPosition;
		 onCheckChange(holder,data,viewType,state);
	}

	/**
	 * if getSelectPosition is  {@link #DEFAULT_POSITION}  there are no selected items
	 * @return select item position
	 */
	public final int getSelectPosition() {
		return lastPosition!=DEFAULT_POSITION?lastPosition-getHeaderCount():lastPosition;
	}

	public boolean isSelect(){
		return lastPosition != DEFAULT_POSITION;
	}

	public void setSelectItem(int position){
	        selectItem(position+getHeaderCount());
	}


	protected abstract void onCheckChange(V holder, T data, int viewType,boolean checked);


	protected abstract void onConverData(V holder, @Nullable T data, int viewType);
}
