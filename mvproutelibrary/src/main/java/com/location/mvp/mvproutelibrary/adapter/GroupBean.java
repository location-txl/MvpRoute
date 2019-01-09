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

	public boolean isInGroup() {
		return inGroup;
	}

	public void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}

	public T getGroup() {
		return group;
	}

	public void setGroup(T group) {
		this.group = group;
	}

	public E getChild() {
		return child;
	}

	public void setChild(E child) {
		this.child = child;
	}

	@Override
	public int getItemType() {
		return inGroup ? TYPE_GROUP : TYPE_CHILD;
	}
}
