package com.location.mvp.mvproutelibrary.api;

import com.location.mvp.mvproutelibrary.Base.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/19 0019 22:39
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface IAPiService {
    @GET("{url}")
    <T> Observable<BaseBean<T>> get(@Path("url") String url);
}
