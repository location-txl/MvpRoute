package com.location.mvp.mvp_route_demo.presenter;

import android.text.TextUtils;

import com.location.mvp.mvp_route_demo.contract.SpContract;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/14 11:25
 * description：
 */

public class SpPresenter extends SpContract.Presenter {
	private SpUtils spUtils;
	public SpPresenter() {
		spUtils = SpUtils.getInstance();
	}

	@Override
	public void save(String key, String value) {
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
			view.showMessage("key或者value不能为空");
			return;
		}
		spUtils.putValue(key,value);
		view.showMessage("保存成功");
	}

	@Override
	public void delete(String key) {
		if (TextUtils.isEmpty(key)) {
			view.showMessage("key不能为空");
			return;
		}
       spUtils.remove(key);
		view.showMessage("删除成功");
	}
}
