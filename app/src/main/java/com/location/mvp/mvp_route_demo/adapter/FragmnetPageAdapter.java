package com.location.mvp.mvp_route_demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.location.mvp.mvproutelibrary.Base.BaseFragment;

import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/5 14:01
 * description：
 */

public class FragmnetPageAdapter extends FragmentPagerAdapter {
	private List<String> title;
	private List<BaseFragment> fragments;

	public FragmnetPageAdapter(FragmentManager fm, List<String> title, List<BaseFragment> fragments) {
		super(fm);
		this.title = title;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return title.get(position);
	}

}
