package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvp_route_demo.contract.PictureContract;
import com.location.mvp.mvproutelibrary.Base.BaseOberver;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;
import com.location.mvp.mvproutelibrary.scheduler.RxResPonse;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/7/2 0002 20:49
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class PicturePresenter extends PictureContract.Presenter {
    @Override
    public void loadPicture() {
        RetrofitClient.getInstance()
                .get().url("api/data/福利/10/1")
                .setBaseUrl("http://gank.io/")
                .create()
                .compose(new RxResPonse.Compose<PictureBean>())
                .subscribe(new BaseOberver<PictureBean>(rxManager,view) {
                    @Override
                    public void onNext(PictureBean pictureBean) {
                                  view.showData(pictureBean.getResults());
                    }
                });
    }
}
