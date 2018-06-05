package com.location.mvp.mvp_route_demo.presenter;

import com.location.mvp.mvp_route_demo.contract.RecyclerContract;
import com.location.mvp.mvp_route_demo.modle.bean.DataBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/30 9:56
 * description：
 */

public class RecyclerPresenter extends RecyclerContract.Presenter {
	@Override
	public void loadData() {
		List<DataBean> list = new ArrayList<>();
		DataBean bean = new DataBean();
		bean.setType(DataBean.TYPE_TEXT);
		bean.setMessage("你好啊");

		DataBean bean1 = new DataBean();
		bean1.setType(DataBean.TYPE_TEXT);
		bean1.setMessage("dsadsa");
		list.add(bean);
		list.add(bean1);
		view.showData(list);
	}


}
