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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 待监听的ArrayList
 * 删除数据 增加数据时会触发 {@see # AdapterList.ChangeListener}
 *
 * @param <E>
 */
public class AdapterList<E> extends ArrayList<E> {


	private ChangeListener<E> listener;

	@Override
	public boolean add(E e) {
		boolean add = super.add(e);

		if (listener != null && add) {
			listener.add(modCount, e, modCount);
		}
		return add;
	}


	@Override
	public void add(int index, E element) {
		super.add(index, element);
		if (listener != null) {
			listener.add(index, element, modCount);
		}

	}



	@Override
	public E remove(int index) {
		if (listener != null) {
			listener.remove(index, get(index));
		}
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {

		return super.remove(o);
	}

	public void addChangeListener(ChangeListener<E> listener) {
		this.listener = listener;
	}

	public void removeChangeListener() {
		this.listener = null;
	}


	public interface ChangeListener<T> {


		void add(int index, T data, int count);

		void remove(int index, T data);

	}
}
