package com.location.mvp.mvproutelibrary.utils;

import android.content.Context;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/27 0027 13:58
 * 修改人:
 * 修改内容:  尺寸转换类
 * 修改时间:
 */


public class Denutils {

	private Denutils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}


	public static int dptopx(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);
	}
	public static int pxtodp(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp/density+0.5f);
	}
	public static int sptopx(Context context, float sp) {
		float density = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (sp*density+0.5f);
	}

public static  int pxtosp(Context context,float px){
	float density = context.getResources().getDisplayMetrics().scaledDensity;

	return (int) (px/density+0.5f);
}






}
