package com.location.mvp.mvp_route_demo;

import com.location.mvp.mvproutelibrary.Base.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/19 0019 22:57
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface TestService {
    @GET("userInfo")
    Observable<BaseBean<UserBean>> get();
}
