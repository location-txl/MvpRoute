package com.location.mvp.mvproutelibrary.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/6/2 0002 14:22
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface ApiService {
    @GET("{path}")
    Observable<ResponseBody> get(@Path("path") String pathurl, @QueryMap Map<String,String>
            params, @HeaderMap Map<String,String> headers);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> post(@Path("path") String pathurl, @FieldMap Map<String, String>
            params, @HeaderMap Map<String, String> headers);

//    @Multipart
//    @POST("{path}")
//    Observable<ResponseBody>
}
