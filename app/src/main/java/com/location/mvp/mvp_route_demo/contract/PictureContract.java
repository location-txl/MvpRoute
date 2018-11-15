package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.BaseView;

import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: location
 * 创建时间: 2018/7/2 0002 20:40
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface PictureContract {
    interface View extends BaseView{
       void showData(List<PictureBean.ResultsBean> response);
    }
    abstract class Presenter extends BasePresenter<View>{
        public abstract void loadPicture();
    }

}
