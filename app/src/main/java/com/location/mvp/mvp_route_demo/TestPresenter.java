package com.location.mvp.mvp_route_demo;

import android.util.Log;

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
        view.load();
        RetrofitClient retrofitClient = RetrofitClient.getRetrofitClient();
        IAPiService api = retrofitClient.createApi(IAPiService.class);
        api.get()
                .map(RxScheduer.<BBBean>map())
                .compose(RxScheduer.io_main())
                .subscribe(new BaseOberver<BBBean>(rxManager) {
                    @Override
                    public void onNext(BBBean resultsBean) {
                        Log.e("TAG", "tas===>" + resultsBean.getMessage());
                    }
                });
    }
}
