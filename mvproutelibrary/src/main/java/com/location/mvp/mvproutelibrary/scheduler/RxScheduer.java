package com.location.mvp.mvproutelibrary.scheduler;

import com.location.mvp.mvproutelibrary.Base.BaseBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 20:23
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class RxScheduer {

    public static <R> Function<BaseBean<R>, ObservableSource<R>> map() {
        final Function<BaseBean<R>, ObservableSource<R>> function = new Function<BaseBean<R>, ObservableSource<R>>() {


            @Override
            public ObservableSource<R> apply(BaseBean<R> rBaseBean) throws Exception {
                return Observable.just(rBaseBean.getData());
            }
        };
        return function;

    }

    public static ObservableTransformer io_main() {
        ObservableTransformer io = new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
        return io;
    }

    public static <T> Function<Throwable, ObservableSource<T>> handlerException() {
        return new Function<Throwable, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(Throwable throwable) throws Exception {
                return Observable.error(throwable);
            }
        };
    }


}
