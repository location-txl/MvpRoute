package com.location.mvp.mvp_route_demo.presenter;

import android.util.Log;

import com.location.mvp.mvp_route_demo.contract.TestContract;
import com.location.mvp.mvp_route_demo.modle.bean.UserBean;
import com.location.mvp.mvp_route_demo.modle.service.TestService;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduer;

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

        TestService api = RetrofitClient.getRetrofitClient().createApi(TestService.class);
        api.get("txl")
                .flatMap(RxScheduer.<UserBean>map())
                .onErrorResumeNext(RxScheduer.<UserBean>handlerException())
                .compose(RxScheduer.io_main())
                .subscribe(new BaseOberver<UserBean>(rxManager, view) {
                    @Override
                    public void onNext(UserBean userBean) {
                        view.load(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("TAG", "error===>" + e.getMessage());
                    }
                });
//        api.post("tianxiaolong")
//                .flatMap(RxScheduer.<LoginBean>map())
//                .compose(RxScheduer.io_main())
//                .subscribe(new BaseOberver(rxManager, view) {
//                    @Override
//                    public void onNext(Object o) {
//
//                    }
//                });

    }
}
