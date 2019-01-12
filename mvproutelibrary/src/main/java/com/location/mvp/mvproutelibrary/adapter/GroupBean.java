package com.location.mvp.mvproutelibrary.adapter;

/**
 * @author tianxiaolong
 *         time：2019/1/10 0:10
 *         description：
 */

class GroupBean<T, E> implements MulitTypeListener {
	static final int TYPE_GROUP = 0;
	static final int TYPE_CHILD = 1;
	private boolean inGroup;
	private T group;
	private E child;
	private int groupPosition;
	private int childGroupPosition;
	private boolean expand;
	private boolean showAnim;


	public boolean isShowAnim() {
		return showAnim;
	}

	public void showAnim(boolean showAnim) {
		this.showAnim = showAnim;
	}

	public void toggle(){
		this.expand = !expand;
	}
	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	boolean isInGroup() {
		return inGroup;
	}

	void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}

	T getGroup() {
		return group;
	}

	void setGroup(T group) {
		this.group = group;
	}

	E getChild() {
		return child;
	}

	void setChild(E child) {
		this.child = child;
	}

	int getGroupPosition() {
		return groupPosition;
	}

	void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}

	int getChildGroupPosition() {
		return childGroupPosition;
	}

	void setChildGroupPosition(int childGroupPosition) {
		this.childGroupPosition = childGroupPosition;
	}

	@Override
	public int getItemType() {


		return inGroup ? TYPE_GROUP : TYPE_CHILD;
	}
}
