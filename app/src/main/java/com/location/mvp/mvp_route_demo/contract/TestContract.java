package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 19:40
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface TestContract {

    interface View extends BaseView {
void load();
    }

    abstract class Presenter extends BasePresenter<View> {


       public abstract void ss();
    }
}
