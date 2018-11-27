package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.bean.UploadResponse;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.service.LoginService;
import com.location.mvp.mvproutelibrary.base.BaseObserver;
import com.location.mvp.mvproutelibrary.base.BaseProgressObserver;
import com.location.mvp.mvproutelibrary.http.ProgressRequestBody;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.http.cookie.CookiesManager;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduler;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:41
 * description：
 */

public class NetPresenterVideo extends NetContract.Presenter {

	private final LoginService loginService;

	public NetPresenterVideo() {
		loginService = RetrofitClient.getInstance().createApi(LoginService.class);
	}

	@Override
	public void loginWanAndroid(String userNmae, String passworld) {
		loginService.login(userNmae, passworld)
				.compose(new RxScheduler.compose<LoginResponse>())
				.subscribe(new BaseObserver<LoginResponse>(rxManager, view) {
					@Override
					public void onNext(LoginResponse loginResponse) {
						view.loginSuccful(loginResponse);
					}
				});


	}

	@Override
	public void uploadVideo(File file) {
		BaseProgressObserver<UploadResponse> observer = new BaseProgressObserver<UploadResponse>(rxManager, view) {
			@Override
			public void onProgress(long readWrite, long contentLength) {
				LogUtils.d("progress", "readWrite===>" + readWrite + "\n contentlength===>" + contentLength);
			}

			@Override
			public void uploadSuccful(UploadResponse response, long contentLength) {
				LogUtils.d("progress", "上传succful");
			}
		};
		MultipartBody.Builder builder = new MultipartBody.Builder()
				.setType(MultipartBody.FORM);
		RequestBody imageBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		builder.addFormDataPart("file", file.getName(), imageBody);
		builder.addFormDataPart("app_key", "A7C413FD1FDAC974F64996121A95F417");
		MultipartBody build = builder.build();
		ProgressRequestBody progressRequestBody = new ProgressRequestBody(build, observer);
		MultipartBody.Part part = MultipartBody.Part.create(progressRequestBody);
//		List<MultipartBody.Part> parts = builder.build().parts();
		List<MultipartBody.Part> parts = new ArrayList<>();
		parts.add(part);
		loginService.uploadImage(parts)
				.compose(new RxScheduler.compose<UploadResponse>())
				.subscribe(observer);
	}

	//我连上你的电脑了 嘿嘿嘿 你能控制我的电脑吗  你连上我的  我怎么控制你的。。
	@Override
	public void cleanLogin() {
		SpUtils.getInstance().remove(KeyUtils.USERNAME);
		SpUtils.getInstance().remove(KeyUtils.PASSWORLD);
		SpUtils.getInstance().remove("test");
		CookiesManager.clearAllCookies();
		List<Cookie> cookies = CookiesManager.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.name();
			LogUtils.d("Retrofit", name);
		}

		view.cleanLoginSuccful();

	}

	@Override
	public void getCollectList(String page, String userNmae, String passwrold) {
		loginService.getCollect(page)
				.compose(new RxScheduler.compose<CollectListBean>())
				.subscribe(new BaseObserver<CollectListBean>(rxManager, view) {
					@Override
					public void onNext(CollectListBean collectListBean) {
						view.showCollectList(collectListBean);
					}


				});
	}
}
