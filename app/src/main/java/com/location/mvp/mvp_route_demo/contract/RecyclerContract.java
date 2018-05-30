package com.location.mvp.mvp_route_demo.contract;

import android.provider.ContactsContract;

import com.location.mvp.mvp_route_demo.modle.bean.DataBean;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.Base.BaseView;

import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/30 9:54
 * description：
 */

public interface RecyclerContract {
	interface View extends BaseView{
		void showData(List<DataBean> data);
	}
	abstract class Presenter extends BasePresenter<View>{
		protected abstract  void  loadData();
	}
}
