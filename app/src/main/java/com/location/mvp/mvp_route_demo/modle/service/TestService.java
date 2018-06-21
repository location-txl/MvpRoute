package com.location.mvp.mvp_route_demo.modle.service;

import com.location.mvp.mvp_route_demo.modle.bean.LoginBean;
import com.location.mvp.mvp_route_demo.modle.bean.UserBean;
import com.location.mvp.mvproutelibrary.Base.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @GET("userinfo")
    Observable<BaseBean<UserBean>> get(@Query("dsa") String string);

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseBean<LoginBean>> post(@Field("username") String s);
}
