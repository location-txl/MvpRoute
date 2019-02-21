package com.location.mvp.mvproutelibrary.adapter;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.os.Build;
import android.os.HardwarePropertiesManager;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.View;

import com.location.mvp.mvproutelibrary.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
			onBindGroup(holder, data.getGroup(), holder.getAdapterPosition());
			if (data.isShowAnim()) {
				showAnim(holder, data.isExpand());
				data.showAnim(false);
			}
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
	public abstract void

	onBindChild(V holder, E response, int groupPosition, int childPosition);


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


	public void openAll() {
		int length = groups.size();
		for (int i = 0; i < length; i++) {
			open(i);
		}
	}


	public void closeAll() {
		int length = groups.size();
		for (int i = 0; i < length; i++) {
			close(i);
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

	private void invokeChange(int groupPosition, boolean state) {
		if (changeListener != null) {
			changeListener.onGroupStateChange(groupPosition, state);
		}
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
			throw new SecurityException("The current position is incorrect  position ===>" + position);
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

	public void removeGroup(@IntRange(from = 0) int index) {
		if (index >= groups.size()) return;
		int appropriatePosition = getAppropriatePosition(index);
		LinkedList<GroupBean<T, E>> tempList = new LinkedList<>();
		GroupBean<T, E> groupBean = data.get(appropriatePosition);
		tempList.addLast(groupBean);
		if (groupBean.isInGroup() && groupBean.isExpand()) {
			for (int i = appropriatePosition + 1; i < appropriatePosition + 1 + childs.get(index).size(); i++) {
				tempList.addLast(data.get(i));
			}
		}
		if (index != groups.size() - 1) {
			int dataLength = data.size();
			for (int i = appropriatePosition; i < dataLength; i++) {
				GroupBean<T, E> dataGroup = data.get(i);
				dataGroup.setGroupPosition(dataGroup.getGroupPosition() - 1);
			}

			List<Integer> collect = null;
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
				collect = openData.stream().filter(i -> i >= index).collect(Collectors.toList());
				openData.removeAll(collect);
				collect.forEach(i -> openData.add(i - 1));
			} else {
				collect = new LinkedList<>();
				for (Integer openDatum : openData) {
					if (openDatum >= index) {
						collect.add(openDatum);
					}
				}
				openData.removeAll(collect);
				for (Integer integer : collect) {
					openData.add(integer - 1);
				}
			}
		}


		data.removeAll(tempList);
		groups.remove(index);
		childs.remove(index);
		notifyDataSetChanged();
	}

	/**
	 * @see #loadGroup(int, Object, List, boolean)
	 */
	public void loadGroup(T group, List<E> groupChild, boolean open) {
		loadGroup(groups.size(), group, groupChild, open);

	}

	/**
	 * @see #loadGroup(int, Object, List, boolean)
	 */
	public void loadGroup(T group, List<E> groupChild) {
		loadGroup(groups.size(), group, groupChild, false);
	}

	/**
	 * @see #loadGroup(int, Object, List, boolean)
	 */
	public void loadGroup(@IntRange(from = 0) int index, T group, List<E> groupChild) {
		loadGroup(index, group, groupChild, false);
	}


	public void refreshGroup(@IntRange(from = 0) int groupPosition) {
		if (checkGroup(groupPosition)) {
			int appropriatePosition = getAppropriatePosition(groupPosition);
			int refreshCount = 1;
			if (!canOpen(appropriatePosition)) {
				refreshCount += childs.get(groupPosition).size();
			}
			notifyItemRangeChanged(appropriatePosition, refreshCount);
		}
	}

	public void refreshGroup(@IntRange(from = 0) int groupPosition, T group, List<E> childList) {
		if (checkGroup(groupPosition)) {
			int appropriatePosition = getAppropriatePosition(groupPosition);
			if (group != null) {
				groups.remove(groupPosition);
				groups.add(groupPosition, group);
				data.get(appropriatePosition).setGroup(group);
			}

			if (childList != null) {


				List<E> es = childs.get(groupPosition);
				Iterator<E> newChild = childList.iterator();
				LinkedList<GroupBean<T, E>> tempList = new LinkedList<>();
				for (int i = appropriatePosition + 1; i < appropriatePosition + 1 + es.size(); i++) {
					if (newChild.hasNext()) {
						data.get(i).setChild(newChild.next());
					} else {
						//nothing  new child   need to remove old child
						tempList.addLast(data.get(i));
					}
				}

				if (!tempList.isEmpty() && !newChild.hasNext()) {
					//remove old child
					data.removeAll(tempList);
				} else if(tempList.isEmpty()&&newChild.hasNext()){
					// more new child
					tempList.clear();
					int position = es.size();
					while (newChild.hasNext()) {
						GroupBean<T, E> newChildBean = new GroupBean<>();
						E next = newChild.next();
						newChildBean.setChild(next);
						newChildBean.setGroupPosition(groupPosition);
						newChildBean.setChildGroupPosition(position);
						position++;
						tempList.addLast(newChildBean);
					}
					//add new child
					data.addAll(appropriatePosition + es.size(), tempList);
				}

				childs.remove(groupPosition);
				childs.add(groupPosition, childList);
//				es.addAll(childList);
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * 增加分组
	 *
	 * @param index      插入时的索引
	 * @param group      分组data
	 * @param groupChild 分组子项的listData
	 * @param open       分组是否自动打开
	 */
	public void loadGroup(@IntRange(from = 0) int index, T group, List<E> groupChild, boolean open) {
		boolean isLast = groups.size() - 1 == index;
		groups.add(index, group);
		childs.add(index, groupChild);
		List<GroupBean<T, E>> tempList = new LinkedList<>();
		int appropriatePosition = getAppropriatePosition(index);

		GroupBean<T, E> groupBean = new GroupBean<>();
		groupBean.setGroupPosition(index);
		groupBean.setExpand(open);
		groupBean.setInGroup(true);
		groupBean.setGroup(group);
		tempList.add(groupBean);
		if (!isLast) {
			int dataLength = data.size();
			for (int i = appropriatePosition; i < dataLength; i++) {
				GroupBean<T, E> dataGroup = data.get(i);
				dataGroup.setGroupPosition(dataGroup.getGroupPosition() + 1);
			}
			List<Integer> collect = null;
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
				collect = openData.stream().filter(i -> i >= index).collect(Collectors.toList());
				openData.removeAll(collect);
				collect.forEach(i -> openData.add(i + 1));
			} else {
				collect = new LinkedList<>();
				for (Integer openDatum : openData) {
					if (openDatum >= index) {
						collect.add(openDatum);
					}
				}
				openData.removeAll(collect);
				for (Integer integer : collect) {
					openData.add(integer + 1);
				}
			}


		}

		if (open) {
			for (int i = 0; i < groupChild.size(); i++) {
				GroupBean<T, E> childBean = new GroupBean<>();
				childBean.setGroupPosition(index);
				childBean.setChildGroupPosition(i);
				childBean.setChild(groupChild.get(i));
				tempList.add(childBean);
			}
		}

		data.addAll(appropriatePosition, tempList);


		//TODO  使用这个局部刷新有短暂的白屏时间  具体查看demo 暂时使用全局刷新  发版之前需要解决
//		notifyItemRangeInserted(appropriatePosition,tempList.size());
		notifyDataSetChanged();
//		notifyItemMoved(appropriatePosition,data.size()-appropriatePosition);
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
