package com.location.mvp.mvproutelibrary.adapter;

import android.appwidget.AppWidgetManager;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author tianxiaolong
 *         time：2019/1/10 0:09
 *         description：
 */

public abstract class BaseGroupAdapter<T, E, V extends BaseViewHolder> extends AbstractBaseAdapter<GroupBean<T, E>, V> implements BaseGroupDealListener {

	private OnGroupItemClickListener groupItemClickListener;

	private GroupChangeListener changeListener;
	private List<T> groups;
	private List<List<E>> childs;
	/**
	 * save open position
	 */
	private TreeSet<Integer> openData;

	public BaseGroupAdapter(int groupLayout, int childLayout, List<T> groupList, List<List<E>> childGroupList) {
		super(groupLayout);
		openData = new TreeSet<>();
		this.groups = groupList;
		this.childs = childGroupList;
		addType(GroupBean.TYPE_GROUP, groupLayout);
		addType(GroupBean.TYPE_CHILD, childLayout);
		initData(groupList, childGroupList);

	}


	public void setGroupChangeListener(GroupChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	public void setOnGroupClickListener(OnGroupItemClickListener listener) {
		this.groupItemClickListener = listener;
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
			groupBean.setExpand(true);
			groupBean.setGroupPosition(i);
			data.add(groupBean);
			List<E> tempChildList = childGroupList.get(i);
			openData.add(i);
			//TODO  默认全部展开
			for (int i1 = 0; i1 < tempChildList.size(); i1++) {
				GroupBean<T, E> childBean = new GroupBean<>();
				childBean.setChild(tempChildList.get(i1));
				childBean.setGroupPosition(i);
				childBean.setChildGroupPosition(i1);
				data.add(childBean);
			}
		}
	}


	@Override
	public void conver(V holder, @Nullable GroupBean<T, E> data, int viewType) {
		if (data.isInGroup()) {
			if (data.isShowAnim()) {
				showAnim(holder, data.isExpand());
				data.showAnim(false);
			}
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


	public void showAnim(V holder, boolean state) {
	}

	@Override
	protected void registerListener(V holder) {
		holder.registerGroupListener(getHeaderCount(), this);
	}


	/**
	 * open group
	 *
	 * @param groupPosition
	 */
	public void open(int groupPosition) {
		if (checkGroup(groupPosition)) {
			int appropriatePosition = getAppropriatePosition(groupPosition);
			if (canOpen(appropriatePosition)) {
				openPosition(appropriatePosition);
			}
		}
	}


	/**
	 * close group
	 *
	 * @param groupPosition
	 */
	public void close(int groupPosition) {
		if (checkGroup(groupPosition)) {
			int appropriatePosition = getAppropriatePosition(groupPosition);
			if (!canOpen(appropriatePosition)) {
				closePosition(appropriatePosition);
			}
		}
	}


	/**
	 * switch group
	 *
	 * @param groupPosition
	 */
	public void toggle(int groupPosition) {
		if (checkGroup(groupPosition)) {
			int appropriatePosition = getAppropriatePosition(groupPosition);
			if (canOpen(appropriatePosition)) {
				openPosition(appropriatePosition);
			} else {
				closePosition(appropriatePosition);
			}
		}
	}

	private void openPosition(int position) {
		GroupBean<T, E> teGroupBean = data.get(position);
		teGroupBean.toggle();
		int groupPosition = teGroupBean.getGroupPosition();
		List<E> es = childs.get(groupPosition);
		openData.add(teGroupBean.getGroupPosition());
		for (int i = 0; i < es.size(); i++) {
			GroupBean<T, E> childBean = new GroupBean<>();
			childBean.setGroupPosition(groupPosition);
			childBean.setChildGroupPosition(i);
			childBean.setChild(es.get(i));
			data.add(position + i + 1, childBean);
		}
		showAnim(position, teGroupBean);
		invokeChange(groupPosition, true);
		notifyItemRangeInserted(position + 1, es.size());
	}

	private void invokeChange(int groupPosition, boolean state) {
		if (changeListener != null) {
			changeListener.onGroupStateChange(groupPosition, state);
		}
	}

	private void closePosition(int position) {

		GroupBean<T, E> teGroupBean = data.get(position);
		teGroupBean.toggle();
		List<E> es = childs.get(teGroupBean.getGroupPosition());
		openData.remove(teGroupBean.getGroupPosition());
		for (int i = 0; i < es.size(); i++) {
			data.remove(position + 1);
		}
		showAnim(position, teGroupBean);
		invokeChange(teGroupBean.getGroupPosition(), true);
		notifyItemRangeRemoved(position + 1, es.size());
	}

	/**
	 * adapter Open or close
	 *
	 * @param position
	 * @return true  is open
	 * false is close
	 * @throws SecurityException When the group is not obtained by position throw exception
	 */
	private boolean canOpen(int position) {
		GroupBean<T, E> teGroupBean = data.get(position);
		if (!teGroupBean.isInGroup()) {
			throw new SecurityException("The current position is incorrect ");
		}
		return !teGroupBean.isExpand();
	}

	private void showAnim(int position, GroupBean<T, E> groupBean) {
		groupBean.showAnim(true);
		notifyItemChanged(position);
	}

	/**
	 * get a correct index  to adapter
	 *
	 * @param groupPosition
	 * @return
	 */
	private int getAppropriatePosition(int groupPosition) {
		int index = groupPosition;
		for (int i = 0; i < groupPosition; i++) {
			if (openData.contains(i)) {
				index += childs.get(i).size();
			}
		}
		return index;
	}


	/**
	 * test  position is it legal
	 *
	 * @param groupPosition
	 * @return
	 */
	private boolean checkGroup(int groupPosition) {
		if (groupPosition < groups.size()) {
			return true;
		}
		return false;
	}

	@Override
	public void dealItem(int position, View itemview) {

		if (groupItemClickListener != null) {
			GroupBean<T, E> teGroupBean = data.get(position);
			if (teGroupBean.isInGroup()) {
				boolean expand = teGroupBean.isExpand();
				if (expand) {
					closePosition(position);
				} else {
					openPosition(position);
				}
				groupItemClickListener.onGroupItemClick(itemview, teGroupBean.getGroupPosition());
			} else {
				groupItemClickListener.onChildItemClick(itemview, teGroupBean.getGroupPosition(), teGroupBean.getChildGroupPosition());
			}
		}
	}
}
