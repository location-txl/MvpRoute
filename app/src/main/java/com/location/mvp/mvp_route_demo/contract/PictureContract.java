package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvp_route_demo.modle.bean.PictureBean;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/5 14:23
 * description：
 */

public interface PictureContract {
	interface View extends BaseView{
		void showData(PictureBean pictureBean);
	}
	abstract class Presenter extends BasePresenter<View>{
		public abstract void loadPicture();
	}
}

