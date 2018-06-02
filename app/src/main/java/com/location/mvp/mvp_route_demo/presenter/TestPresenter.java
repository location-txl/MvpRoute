package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.App;
import com.location.mvp.mvp_route_demo.contract.TestContract;
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
        App.client
                .get()
                .url("api/data/Android/10/1")
                .build()
                .compose(RxScheduer.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        LogUtils.e("TAG", responseBody.string());
                    }
                });
//        TestService api = RetrofitClient.getRetrofitClient().createApi(TestService.class);
//        api.get("txl")
//                .flatMap(RxScheduer.<UserBean>map())
//                .onErrorResumeNext(RxScheduer.<UserBean>handlerException())
//                .compose(RxScheduer.io_main())
//                .subscribe(new BaseOberver<UserBean>(rxManager, view) {
//                    @Override
//                    public void onNext(UserBean userBean) {
//                        view.load(userBean);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        Log.e("TAG", "error===>" + e.getMessage());
//                    }
//                });

    }
}
