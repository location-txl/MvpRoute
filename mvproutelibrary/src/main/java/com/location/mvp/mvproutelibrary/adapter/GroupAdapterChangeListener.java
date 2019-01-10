package com.location.mvp.mvproutelibrary.adapter;

/**
 * @author tianxiaolong
 *         time：2019/1/10 13:54
 *         description：
 */

public interface GroupAdapterChangeListener<V extends BaseViewHolder> {
	/**
	 * 分组模式下状态变化监听接口
	 * @param holder       viewHolder
	 * @param groupPosition 变换的索引
	 * @param state         true 展开  false  闭合
	 */
	void onGroupStateChange(V holder,int groupPosition, boolean state);
}
