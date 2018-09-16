package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.bean.ZcChildBean;
import com.location.mvp.mvp_route_demo.bean.ZcDataBean;
import com.location.mvp.mvp_route_demo.contract.ZcContract;
import com.location.mvp.mvp_route_demo.view.activity.ZcActivity;
import com.location.mvp.mvp_route_demo.view.activity.ZcChildActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 19:58
 * description：
 */

public class ZcPresenter extends ZcContract.Presenter {
	@Override
	public void loadData() {

	}

	@Override
	public void insertItem() {
		ZcDataBean zcDataBean = new ZcDataBean();
		view.insertItemSuccful(zcDataBean);
	}



	@Override
	public void openChildActivity(ZcActivity activity, ArrayList<ZcChildBean> response, int requestCode,int position) {
         activity.startActivityForResult(ZcChildActivity.start(activity,response,position),requestCode);



	}

	@Override
	public void openChildActivity(ZcActivity activity, ArrayList<ZcChildBean> response) {
activity.startActivity(ZcChildActivity.start(activity,response));
	}


}
