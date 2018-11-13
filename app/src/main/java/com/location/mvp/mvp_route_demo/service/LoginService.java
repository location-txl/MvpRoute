package com.location.mvp.mvp_route_demo.service;

import com.location.mvp.mvp_route_demo.base.BaseData;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.bean.UploadResponse;
import com.location.mvp.mvproutelibrary.Base.BaseProgressObserver;
import com.location.mvp.mvproutelibrary.http.Progress;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
	Observable<LoginResponse> login(@Field("username") String username, @Field("password") String passworld);


	@GET("lg/collect/list/{page}/json")
	Observable<CollectListBean> getCollect(@Path("page") String page);


	@GET("lg/collect/list/{page}/json")
	Observable<CollectListBean> s(@Path("page") String page );

	@Multipart
	@POST("/?s=App.CDN.UploadImg")
//	@Progress()
	Observable<UploadResponse> uploadImage(@Part List<MultipartBody.Part> partList);
}
