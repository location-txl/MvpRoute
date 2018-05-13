package com.location.mvp.mvp_route_demo;

import com.location.mvp.mvproutelibrary.Base.BaseBean;

import retrofit2.http.GET;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 22:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface IAPiService {
    @GET("tt")
    io.reactivex.Observable<BaseBean<BBBean>> get();

}
