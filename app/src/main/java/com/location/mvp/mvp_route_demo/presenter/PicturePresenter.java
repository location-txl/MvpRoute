package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.contract.PictureContract;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/7/2 0002 20:49
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class PicturePresenter extends PictureContract.Presenter {
    @Override
    public void loadPicture() {

//                .compose(new RxScheduer.IO_MAIN<ResponseBody>())
//                .subscribe(new BaseOberver<ResponseBody>(rxManager, view) {
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            LogUtils.d(responseBody.string());
//                        } catch (IOException e) {
////                            e.printStackTrace();
//                            LogUtils.d(e.getMessage());
//                        }
//                    }
//                });
    }
}
