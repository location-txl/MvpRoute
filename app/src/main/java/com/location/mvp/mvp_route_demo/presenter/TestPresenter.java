package com.location.mvp.mvp_route_demo.presenter;

import android.util.Log;

import com.location.mvp.mvp_route_demo.App;
import com.location.mvp.mvp_route_demo.contract.TestContract;
import com.location.mvp.mvp_route_demo.modle.bean.UserBean;
import com.location.mvp.mvp_route_demo.modle.service.TestService;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxResPonse;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 19:59
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class TestPresenter extends TestContract.Presenter {

	@Override
	public void ss() {
//        App.client
//                .get()
//                .url("api/data/Android/10/1")
//                .build()
//                .compose(new RxScheduer.IO_MAIN<ResponseBody>())
//                .subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        LogUtils.e("TAG", responseBody.string());
//                    }
//                });
		TestService api = App.client.createApi(TestService.class);
		App.client.get()
				.url("111")
				.addParams("123", "123")
				.addParams("123", "123")
				.addHeader("123", "123")
				.create()
				.compose(new RxResPonse.Compose<UserBean>())
				.subscribe(new Consumer<UserBean>() {
					@Override
					public void accept(UserBean userBean) throws Exception {

					}
				});
//		api.get("txl")
//				.map(new RxScheduer.map<UserBean>())
//				.onErrorResumeNext(RxScheduer.<UserBean>handlerException())
//				.compose(new RxScheduer.IO_MAIN<UserBean>())
//				.subscribe(new BaseOberver<UserBean>(rxManager, view) {
//					@Override
//					public void onNext(UserBean userBean) {
//						view.load(userBean);
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						super.onError(e);
//						Log.e("TAG", "error===>" + e.getMessage());
//					}
//				});

	}
}
