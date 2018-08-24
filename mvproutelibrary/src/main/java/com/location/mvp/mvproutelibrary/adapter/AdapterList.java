package com.location.mvp.mvproutelibrary.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *  待监听的ArrayList
 *  删除数据 增加数据时会触发 {@see # AdapterList.ChangeListener}
 * @param <E>
 */
public class AdapterList<E> extends ArrayList<E> {


    private ChangeListener<E> listener;

    @Override
    public boolean add(E e) {
        if (listener != null) {
            listener.add(modCount, e);
        }
        return super.add(e);
    }


    @Override
    public void add(int index, E element) {
        super.add(index, element);
        if(listener!=null){
            listener.add(index, element);
        }

    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(listener!=null){
            Iterator<? extends E> iterator = c.iterator();
            int index = modCount;
            while (iterator.hasNext()) {
                E next = iterator.next();
                listener.add(index, next);
                index++;
            }
        }

        return super.addAll(c);
    }

    @Override
    public E remove(int index) {
        if(listener!=null){
            listener.remove(index,get(index));
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


        void add(int index, T data);

        void remove(int index, T data);

    }
}
