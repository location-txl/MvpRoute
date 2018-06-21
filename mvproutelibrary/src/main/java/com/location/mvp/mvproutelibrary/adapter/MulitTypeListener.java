package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.IntRange;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/19 11:37
 * description：
 */

public interface MulitTypeListener {
	@IntRange(from = 0)
	int getItemType();
}
