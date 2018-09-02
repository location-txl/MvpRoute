package com.location.mvp.mvp_route_demo.contract;

import com.location.mvp.mvp_route_demo.bean.ZcChildBean;
import com.location.mvp.mvp_route_demo.bean.ZcDataBean;
import com.location.mvp.mvp_route_demo.view.activity.ZcActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 19:47
 * description：
 */

public interface ZcContract {
	interface View extends BaseView {
		void showData(List<ZcDataBean> response);
		void insertItemSuccful(ZcDataBean zcDataBean);
	}

	abstract class Presenter extends BasePresenter<View> {

		public abstract void loadData();

		public abstract void insertItem();
		public abstract void openChildActivity(ZcActivity activity, ArrayList<ZcChildBean> response, int requestCode,int position);
       public abstract void openChildActivity(ZcActivity activity,ArrayList<ZcChildBean> response);
	}
}
