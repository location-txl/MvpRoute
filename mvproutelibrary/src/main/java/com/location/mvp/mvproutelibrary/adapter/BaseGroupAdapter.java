package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

/**
 * @author tianxiaolong
 *         time：2019/1/10 0:09
 *         description：
 */

public abstract class BaseGroupAdapter<T, E, V extends BaseViewHolder> extends AbstractBaseAdapter<GroupBean<T, E>, V> implements BaseGroupDealListener {

	private OnGroupItemClickListener grouItemClickListener;

	public BaseGroupAdapter(int groupLayout, int childLayout, List<T> groupList, List<List<E>> childGroupList) {
		super(groupLayout);
		addType(GroupBean.TYPE_GROUP, groupLayout);
		addType(GroupBean.TYPE_CHILD, childLayout);
		initData(groupList, childGroupList);

	}

	public void setOnGroupClickListener(OnGroupItemClickListener listener) {
		this.grouItemClickListener = listener;
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
		if (data.isInGroup()) {
			onBindGroup(holder, data.getGroup(), holder.getAdapterPosition());
		} else {
			onBindChild(holder, data.getChild(), 0, 0);
		}

	}

	/**
	 * d
	 *
	 * @param holder
	 * @param response
	 * @param groupPosition
	 */
	public abstract void onBindGroup(V holder, T response, int groupPosition);

	/**
	 * 1
	 *
	 * @param holder
	 * @param response
	 * @param groupPosition
	 * @param childPosition
	 */
	public abstract void onBindChild(V holder, E response, int groupPosition, int childPosition);

	@Override
	protected void registerListener(V holder) {
		holder.registerGroupListener(getHeaderCount(), this);
	}

	@Override
	public void dealItem(int position, View itemview) {
		if (grouItemClickListener != null) {
			GroupBean<T, E> teGroupBean = data.get(position);
			if (teGroupBean.isInGroup()) {
				grouItemClickListener.onGroupItemClick(itemview, teGroupBean.getGroupPosition());
			} else {
				grouItemClickListener.onChildItemClick(itemview, teGroupBean.getGroupPosition(), teGroupBean.getChildGroupPosition());
			}
		}
	}
}
