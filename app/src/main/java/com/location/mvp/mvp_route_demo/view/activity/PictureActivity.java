package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.PhotoAdapter;
import com.location.mvp.mvp_route_demo.adapter.PictureAdapter;
import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvp_route_demo.pz.GlideImageLoader;
import com.location.mvp.mvproutelibrary.adapter.OnChildClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.view.BobPopwindow;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/25 16:14
 * description：
 */

public class PictureActivity extends AppCompatActivity implements View.OnClickListener, PictureAdapter.onPictureClick {

	private final int REQUEST_CODE_SELECT = 0x001;
	private final int REQUEST_CODE_PREVIEW = 0x002;
	private final int REQUEST_CODE_CAMERA = 0x002;

	private RecyclerView recyclerView;

	private List<PictureBean> beanList;


	private PictureAdapter adapter;

	private BobPopwindow popwindow;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picker);
		initImagePicker();
		initRecyclerView();
		initPopwindow();
	}

	private void initPopwindow() {

		View view = LayoutInflater.from(this).inflate(R.layout.popwindow_select_photo, null);
		popwindow = new BobPopwindow.Builder(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
				.setViewClick(R.id.pop_camera, this)
				.setViewClick(R.id.pop_picture, this)
				.setViewClick(R.id.pop_cancle, this)
				.setDarken(this)
				.setBackground(new ColorDrawable(Color.WHITE))
				.setAlpha(0.8f)
				.create();

	}

	private void initRecyclerView() {
		recyclerView = findViewById(R.id.id_recycler);
		beanList = new ArrayList<>();
		recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

		//点击事件
		adapter = new PictureAdapter(beanList, R.layout.item_picture, this);
		recyclerView.setAdapter(adapter);

	}


	private void initImagePicker() {
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(false);                      //隐藏拍照按钮
		imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
		imagePicker.setSelectLimit(9);              //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
			if (data == null) {
				return;
			}
			switch (requestCode) {
				case REQUEST_CODE_SELECT:
					List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					refresh(images);
					break;
				case REQUEST_CODE_CAMERA:
					List<ImageItem> cameraImg = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					ImageItem imageItem = cameraImg.get(0);
					PictureBean pictureBean = new PictureBean();
					pictureBean.setLocaUrl(imageItem.path);



					//加载更多
					beanList.add(pictureBean);
					adapter.notifyDataSetChanged();
					break;
				default:
			}

		} else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
			//预览图片返回
			if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
				List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
				refresh(images);
			}
		}
	}


	/**
	 * 格式化单位
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
//            return size + "Byte";
			return "0K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

	private void refresh(List<ImageItem> images) {
		io.reactivex.Observable.fromIterable(images)
				.map(new Function<ImageItem, PictureBean>() {
					@Override
					public PictureBean apply(ImageItem imageItem) throws Exception {
						PictureBean pictureBean = new PictureBean();
						pictureBean.setLocaUrl(imageItem.path);
						return pictureBean;
					}
				}).subscribe(new Observer<PictureBean>() {
			@Override
			public void onSubscribe(Disposable d) {
//				beanList.clear();
			}

			@Override
			public void onNext(PictureBean pictureBean) {
				beanList.add(pictureBean);
				LogUtils.d("原图片大小===>" + getFormatSize(new File(pictureBean.getLocaUrl()).length()));
				LogUtils.d("开始压缩-----------");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {
				//刷新数据源
				adapter.notifyDataSetChanged();
			}
		});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.pop_camera:
				ImagePicker.getInstance().setSelectLimit(9);
				Intent intent = new Intent(this, ImageGridActivity.class);
				intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
				startActivityForResult(intent, REQUEST_CODE_CAMERA);


				break;
			case R.id.pop_picture:
				//打开选择,本次允许选择的数量
				ImagePicker.getInstance().setSelectLimit(9 - beanList.size());
				Intent intent1 = new Intent(PictureActivity.this, ImageGridActivity.class);
//				intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, data);
				startActivityForResult(intent1, REQUEST_CODE_SELECT);
				break;
			default:
		}
		popwindow.dismiss();
	}


	//预览图片
	@Override
	public void onPriewPicture(int position) {
		ArrayList<ImageItem> list = new ArrayList<>();
		for (PictureBean pictureBean : beanList) {
			ImageItem imageItem = new ImageItem();
			imageItem.path = pictureBean.getLocaUrl();
			list.add(imageItem);
		}
		Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
		intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
		intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
		intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
		startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
	}

	/**
	 * 质量压缩
	 */
	public static Bitmap compressImage(Bitmap image, String filepath) {
//		try {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 500) {    //循环判断如果压缩后图片是否大于500kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
		}
		LogUtils.d("压缩后大小===>" + getFormatSize(baos.size()));

//			//压缩好后写入文件中
//			FileOutputStream fos = new FileOutputStream(filepath);
//			fos.write(baos.toByteArray());
//			fos.flush();
//			fos.close();
		return image;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
	}

	//删除图片
	@Override
	public void onDeletePicture(int position) {
		beanList.remove(position);
		adapter.notifyDataSetChanged();
	}

	//选择图片
	@Override
	public void onSelectPicture(int position) {
//选择图片
		popwindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}
}
