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

import android.view.View;


/**
 * 子view的点击事件
 */
public interface OnChildClickListener {
	/**
	 * 子view的点击事件
	 * @param viewHolder viewholer
	 * @param view    点击的view
	 * @param position 当前的索引
	 */
	void onChildClcik(ViewHolder viewHolder, View view, int position);
}
