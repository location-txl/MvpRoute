/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 头尾布局的点击事件
 */

public interface OnHeaderClickListener {
	/**
	 *headerFotter点击事件
	 * @param layout  布局ID
	 * @param view view
	 * @param data  数据源
	 * @param position  当前坐标索引 不是RecyclerView的索引 而是相对于这个布局
	 * @param isHeader  是否是头布局
	 */
	void onHeaderClick( int layout, View view, @Nullable Object data, int position, boolean isHeader);
}
