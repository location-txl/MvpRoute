package com.location.mvp.contract;

import android.content.Context;

import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.BaseView;

import java.util.List;

/**
 * @author tianxiaolong
 * time：2019-08-25 14:48
 * description：
 */
public interface HomeContract {

	public abstract  class HomePresenter extends BasePresenter<HomeView>{
		public abstract void loadList(Context context);
	}

	public interface HomeView extends BaseView{

		void showList(List<String> list);
	}
}
