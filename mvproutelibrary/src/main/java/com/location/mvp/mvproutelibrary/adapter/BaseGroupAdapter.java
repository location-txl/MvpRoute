package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author tianxiaolong
 *         time：2019/1/10 0:09
 *         description：
 */

public abstract class BaseGroupAdapter<T, E, V extends BaseViewHolder> extends BaseAdapter<GroupBean<T, E>, V> {


	public BaseGroupAdapter(int groupLayout, int childLayout, List<T> groupList, List<List<E>> childGroupList) {
		super(groupLayout);
		addType(GroupBean.TYPE_GROUP,groupLayout);
		addType(GroupBean.TYPE_CHILD, childLayout);
		initData(groupList, childGroupList);

	}

	private void initData(List<T> groupList, List<List<E>> childGroupList) {
		if (groupList == null || groupList.isEmpty() || childGroupList == null || childGroupList.isEmpty()) {
			return;
		}
		//TODO  是为了遍历方便 后期需要考察
		if (groupList.size() > childGroupList.size()) return;
		int groupLength = groupList.size();
		for (int i = 0; i < groupLength; i++) {
			T group = groupList.get(i);
			GroupBean<T, E> groupBean = new GroupBean<>();
			groupBean.setGroup(group);
			groupBean.setInGroup(true);
			data.add(groupBean);
			List<E> tempChildList = childGroupList.get(i);
			//TODO  默认全部展开
			for (E child : tempChildList) {
				GroupBean<T, E> childBean = new GroupBean<>();
				childBean.setChild(child);
				data.add(childBean);
			}
		}
	}


	@Override
	public void conver(V holder, @Nullable GroupBean<T, E> data, int viewType) {
		if(data.isInGroup()){
			onBindGroup(data.getGroup(), holder.getAdapterPosition());
		}else{
			onBindChild(data.getChild(),0,0);
		}
	}

	public abstract void onBindGroup(T response, int groupPosition);

	public abstract void onBindChild(E response, int groupPosition, int childPosition);
}
