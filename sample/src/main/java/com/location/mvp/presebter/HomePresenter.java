package com.location.mvp.presebter;

import android.content.Context;

import com.location.mvp.R;
import com.location.mvp.contract.HomeContract;

import java.util.Arrays;

/**
 * @author tianxiaolong
 * time：2019-08-25 14:51
 * description：
 */
public class HomePresenter extends HomeContract.HomePresenter {
	@Override
	public void loadList(Context context) {
		String[] stringArray = context.getResources().getStringArray(R.array.home_list);
		view.showList(Arrays.asList(stringArray));
	}
}
