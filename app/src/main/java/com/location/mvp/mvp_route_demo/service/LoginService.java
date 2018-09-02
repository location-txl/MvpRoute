package com.location.mvp.mvp_route_demo.service;

import com.location.mvp.mvp_route_demo.base.BaseData;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:30
 * description：
 */

public interface LoginService {
	@FormUrlEncoded
	@POST("user/login")
	Observable<BaseData<LoginResponse>> login(@Field("username") String username, @Field("password") String passworld);


	@GET("lg/collect/list/{page}/json")
	Observable<BaseData<CollectListBean>> getCollect(@Path("page") String page, @Header("Cookie") String username,@Header("Cookie") String passworld);

}
