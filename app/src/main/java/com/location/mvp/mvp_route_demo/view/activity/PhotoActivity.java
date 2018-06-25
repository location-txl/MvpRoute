package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Intent;
import android.database.Observable;
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
import com.location.mvp.mvp_route_demo.bean.PictureBean;
import com.location.mvp.mvp_route_demo.pz.GlideImageLoader;
import com.location.mvp.mvproutelibrary.adapter.OnChildClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.listener.OnNoDoubleClickListener;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.view.BobPopwindow;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

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

public class PhotoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnChildClickListener, View.OnClickListener {

	private final int REQUEST_CODE_SELECT = 0x001;
	private final int REQUEST_CODE_PREVIEW = 0x002;
	private final int REQUEST_CODE_CAMERA = 0x002;

	private RecyclerView recyclerView;

	private List<PictureBean> beanList;

	private PhotoAdapter adapter;
	private BobPopwindow popwindow;

	//选择的
	private ArrayList<ImageItem> data;

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
		adapter = new PhotoAdapter(beanList, R.layout.item_picture, this);

		//设置删除图片的点击事件
		adapter.setOnChildClickListener(R.id.item_delete, this);
		recyclerView.setAdapter(adapter);

	}


	private void initImagePicker() {
		data = new ArrayList<>();
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
			if(data==null){
				return;
			}
			switch (requestCode){
				case REQUEST_CODE_SELECT:
					List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					refresh(images);
					this.data.clear();
					this.data.addAll(images);
					break;
				case REQUEST_CODE_CAMERA:
					List<ImageItem> cameraImg = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					if(this.data==null){
						this.data = new ArrayList<>();
					}
					ImageItem imageItem = cameraImg.get(0);
					PictureBean pictureBean = new PictureBean();
					pictureBean.setLocaUrl(imageItem.path);
					adapter.loadItem(pictureBean);
					adapter.notifyDataSetChanged();
					this.data.add(imageItem);
					beanList.add(pictureBean);
					break;
					default:
			}

		} else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
			//预览图片返回
			if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
				List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
				refresh(images);
				this.data.clear();
				this.data.addAll(images);
			}
		}
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
				beanList.clear();
			}

			@Override
			public void onNext(PictureBean pictureBean) {
				beanList.add(pictureBean);

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {
				adapter.refresh(beanList);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position >= beanList.size()) {
			//选择图片
			popwindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		} else {
			Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
			intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, data);
			intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
			intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
			startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
		}
	}

	/**
	 * 删除
	 *
	 * @param viewHolder
	 * @param view
	 * @param position
	 */
	@Override
	public void onChildClcik(ViewHolder viewHolder, View view, int position) {
		data.remove(position);
		beanList.remove(position);
		adapter.remove(position);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.pop_camera:
				ImagePicker.getInstance().setSelectLimit(9);
				Intent intent = new Intent(this, ImageGridActivity.class);
				intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, data);
				intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
				startActivityForResult(intent, REQUEST_CODE_CAMERA);
				break;
			case R.id.pop_picture:
				//打开选择,本次允许选择的数量
				ImagePicker.getInstance().setSelectLimit(9);
				Intent intent1 = new Intent(PhotoActivity.this, ImageGridActivity.class);
				intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, data);
				startActivityForResult(intent1, REQUEST_CODE_SELECT);
				break;
			default:
		}
		popwindow.dismiss();
	}
}
