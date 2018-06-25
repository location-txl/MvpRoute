package com.location.mvp.mvp_route_demo.pz;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.location.mvp.mvp_route_demo.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/25 16:31
 * description：
 */

public class GlideImageLoader implements ImageLoader {
	@Override
	public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
		////配置上下文
		Glide.with(activity)
				////设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
				.load(Uri.fromFile(new File(path)))
				////设置错误图片
				.error(R.drawable.ic_default_image)
				// //设置占位图片
				.placeholder(R.drawable.ic_default_image)
				////缓存全尺寸
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(imageView);
	}

	@Override
	public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
		////配置上下文
		Glide.with(activity)
				// //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
				.load(Uri.fromFile(new File(path)))
				//缓存全尺寸
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(imageView);
	}

	@Override
	public void clearMemoryCache() {

	}
}
