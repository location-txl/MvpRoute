package com.location.mvp.mvp_route_demo.view.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.location.mvp.mvp_route_demo.GetPathFromUri;
import com.location.mvp.mvp_route_demo.KeyUtils;
import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.CollectListBean;
import com.location.mvp.mvp_route_demo.bean.LoginResponse;
import com.location.mvp.mvp_route_demo.contract.NetContract;
import com.location.mvp.mvp_route_demo.presenter.NetPresenter;
import com.location.mvp.mvp_route_demo.presenter.NetPresenterVideo;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.Request;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.LogUtils;
import com.location.mvp.mvproutelibrary.utils.SpUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/31 11:39
 * description：
 */

public class NetActivity extends BaseToActivity<NetContract.Presenter> implements NetContract.View {

	private File imageFile;
	private Uri imageUri;

	@Override
	public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {
		ToastUtils.showShort(baseThrowable.msg);
//		LogUtils.d("错误",baseThrowable.getCause().getMessage());
	}

	@Override
	protected String getTooBarTitle() {
		return "网络请求";
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_net;
	}

	@Override
	protected void loadData() {
		final EditText pwd = findViewById(R.id.net_edit);
		findViewById(R.id.net_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.loginWanAndroid("tianxiaolong", pwd.getText().toString());
			}
		});
		findViewById(R.id.net_clean).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				presenter.cleanLogin();
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// 以startActivityForResult的方式启动一个activity用来获取返回的结果
				startActivityForResult(intent, 101);

			}
		});
		findViewById(R.id.net_collect).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = "loginUserName=" + SpUtils.getInstance().getString(KeyUtils.USERNAME);
				String passwrold = "loginUserPassword=" + SpUtils.getInstance().getString(KeyUtils.PASSWORLD);

				for (int i = 0; i < 5; i++) {
					presenter.getCollectList("0", username, passwrold);

				}
			}
		});
	}

	@NonNull
	@Override
	protected NetContract.Presenter createPresenter() {
		return new NetPresenterVideo();
	}

	@Request(request = 100, result = RESULT_OK)
	private void getVideo(Intent data) {

				ToastUtils.showShort("开始上传");
				presenter.uploadVideo(imageFile);



	}

	@Override
	public void loginSuccful(LoginResponse response) {
		SpUtils instance = SpUtils.getInstance();
		instance.putValue(KeyUtils.USERNAME, response.getUsername());
		instance.putValue(KeyUtils.PASSWORLD, response.getPassword());
		ToastUtils.showShort("登录成功");
	}

	@Override
	public void showCollectList(CollectListBean response) {
		ToastUtils.showShort("获取收藏列表成功");
	}

	@Override
	public void cleanLoginSuccful() {

		ToastUtils.showShort("清除登录状态成功");
	}

	@Override
	public int[] hideSoftByEditViewIds() {


		return super.hideSoftByEditViewIds();
	}
    @Request(request =  101,result = RESULT_OK)
	public void getpData(Intent data){
		Uri uri = data.getData();// 获取图片的uri
		Intent intent_gallery_crop = new Intent("com.android.camera.action.CROP");
		intent_gallery_crop.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent_gallery_crop.putExtra("crop", "true");
		intent_gallery_crop.putExtra("scale", true);
		// aspectX aspectY 是宽高的比例
		intent_gallery_crop.putExtra("aspectX", 1);
		intent_gallery_crop.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent_gallery_crop.putExtra("outputX", 400);
		intent_gallery_crop.putExtra("outputY", 400);

		intent_gallery_crop.putExtra("return-data", false);

		// 创建文件保存裁剪的图片
		createImageFile();
		imageUri = Uri.fromFile(imageFile);
		if (imageUri != null) {
			intent_gallery_crop.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent_gallery_crop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		}
		startActivityForResult(intent_gallery_crop, 100);
	}


	/**
	 * 创建File保存图片
	 */
	private void createImageFile() {

		try {

			if (imageFile != null && imageFile.exists()) {
				imageFile.delete();
			}
			// 新建文件
			imageFile = new File(Environment.getExternalStorageDirectory(),
					System.currentTimeMillis() + "galleryDemo.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

