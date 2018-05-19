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

    public static <R extends BaseBean> Function<BaseBean, R> map() {
        final Function<BaseBean, R> function = new Function<BaseBean, R>() {

            @Override
            public R apply(BaseBean baseBean) throws Exception {
                //dsadsa
                return (R) baseBean.getData();
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


}
