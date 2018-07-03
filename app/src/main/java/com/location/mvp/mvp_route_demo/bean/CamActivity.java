package com.location.mvp.mvp_route_demo.bean;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.m1760.aunt_side.R;
import com.example.m1760.aunt_side.controller.activity.Person_Video_Activity;
import com.example.m1760.aunt_side.controller.gaode.ToastUtil;
import com.example.m1760.aunt_side.moudel.OkHttp;
import com.example.m1760.aunt_side.moudel.bean.Video_Upload_Bean;
import com.example.m1760.aunt_side.util.HostUtil;
import com.example.m1760.aunt_side.view.Loading_view;
import com.google.gson.Gson;
import com.googlecode.mp4parser.authoring.Movie;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名称: WanAndroid
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/4/19 0019 11:18
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class CamActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, MyOrientationDetector.OrientationListener {
	public static final String KEY_TIME = "key_maxTime";
	public static final String KEY_FILE = "key_File";
	List<Movie> list;
	private int maxTime = Integer.MAX_VALUE;
	private static final String TAG = CamActivity.class.getSimpleName();
	private static final int FOCUS_AREA_SIZE = 500;
	private Camera mCamera;
	private CameraPreview mPreview;
	private MediaRecorder mediaRecorder;
	private String url_file;
	private static boolean cameraFront = false;
	private static boolean flash = false;
	private long countUp;
	private int quality = CamcorderProfile.QUALITY_480P;


	private Chronometer textChrono;
	private LinearLayout linearLayout;
	private ImageView errorView;
	private ImageView startImage;
	private ImageView changeImage;
	private TextView minute, second;

	/**
	 * 没有则为true  是否有前置
	 */
	private boolean isQianzhi;

	private int orient;


	boolean recording = false;
	//相机权限,录制音频权限,读写sd卡的权限,都为必须,缺一不可
	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.CAMERA,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE};
	private static final int REQUEST_CODE_FOR_PERMISSIONS = 0;//
	private MyOrientationDetector myOrientationDetector;
	private int RESULT_CODE_FOR_RECORD_VIDEO_SUCCEED = 1;
	private AlertDialog show;
	private SharedPreferences mShared;
	private String saveDir;
	private String bucket;
	private Loading_view loading;
	private String time;
	private SharedPreferences.Editor mEditor;
	private String url;
	private int alerTime;
	private Disposable disposable;
	private boolean isstart;
	private long lastTime;
	private ProgressDialog dialogs;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		mShared = getSharedPreferences("login", MODE_PRIVATE);
		mEditor = mShared.edit();

		PermissionsChecker mChecker = new PermissionsChecker(getApplicationContext());
		if (mChecker.lacksPermissions(PERMISSIONS)) {
			PermissionsActivity.startActivityForResult(this, REQUEST_CODE_FOR_PERMISSIONS, PERMISSIONS);
		}
		initview();
		myOrientationDetector = new MyOrientationDetector(this, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		myOrientationDetector.enable();
		if (!hasCamera(getApplicationContext())) {
			//这台设备没有发现摄像头
			Toast.makeText(getApplicationContext(), "对不起,没有发现摄像头,请检查"
					, Toast.LENGTH_SHORT).show();
			releaseCamera();
			releaseMediaRecorder();
			finish();
		}

		if (mCamera == null) {
			releaseCamera();
			final boolean frontal = cameraFront;

			int cameraId = findFrontFacingCamera();
			if (cameraId < 0) {
				//前置摄像头不存在
				isQianzhi = true;

				//尝试寻找后置摄像头
				cameraId = findBackFacingCamera();
				if (flash) {
					mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				}
			} else if (!frontal) {
				cameraId = findBackFacingCamera();
				if (flash) {
					mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				}
			}

			mCamera = Camera.open(cameraId);
			mPreview.refreshCamera(mCamera);
			reloadQualities(cameraId);

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
		myOrientationDetector.disable();
	}

	//修改录像质量
	private void changeVideoQuality(int quality) {
		SharedPreferences prefs = getSharedPreferences("RECORDING", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("QUALITY", quality);
		editor.commit();
		this.quality = quality;
		updateButtonText(quality);
	}


	private void updateButtonText(int quality) {
//        if (quality == CamcorderProfile.QUALITY_480P)
//            mBinding.buttonQuality.setText("480p");
//        if (quality == CamcorderProfile.QUALITY_720P)
//            mBinding.buttonQuality.setText("720p");
//        if (quality == CamcorderProfile.QUALITY_1080P)
//            mBinding.buttonQuality.setText("1080p");
//        if (quality == CamcorderProfile.QUALITY_2160P)
//            mBinding.buttonQuality.setText("2160p");
	}

	//reload成像质量
	private void reloadQualities(int idCamera) {
		SharedPreferences prefs = getSharedPreferences("RECORDING", Context.MODE_PRIVATE);

		quality = prefs.getInt("QUALITY", CamcorderProfile.QUALITY_480P);

		changeVideoQuality(quality);

		final ArrayList<String> list = new ArrayList<String>();

		int maxQualitySupported = CamcorderProfile.QUALITY_480P;

		if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_480P)) {
			list.add("480p");
			maxQualitySupported = CamcorderProfile.QUALITY_480P;
		}
		if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_720P)) {
			list.add("720p");
			maxQualitySupported = CamcorderProfile.QUALITY_720P;
		}
		if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_1080P)) {
			list.add("1080p");
			maxQualitySupported = CamcorderProfile.QUALITY_1080P;
		}
		if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_2160P)) {
			list.add("2160p");
			maxQualitySupported = CamcorderProfile.QUALITY_2160P;
		}

		if (!CamcorderProfile.hasProfile(idCamera, quality)) {
			quality = maxQualitySupported;
			updateButtonText(maxQualitySupported);
		}

//        final StableArrayAdapter adapter = new StableArrayAdapter(this,
//                android.R.layout.simple_list_item_1, list);
//        mBinding.listOfQualities.setAdapter(adapter);

//        mBinding.listOfQualities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//
//                mBinding.buttonQuality.setText(item);
//
//                if (item.equals("480p")) {
//                    changeVideoQuality(CamcorderProfile.QUALITY_480P);
//                } else if (item.equals("720p")) {
//                    changeVideoQuality(CamcorderProfile.QUALITY_720P);
//                } else if (item.equals("1080p")) {
//                    changeVideoQuality(CamcorderProfile.QUALITY_1080P);
//                } else if (item.equals("2160p")) {
//                    changeVideoQuality(CamcorderProfile.QUALITY_2160P);
//                }
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    mBinding.listOfQualities.animate().setDuration(200).alpha(0)
//                            .withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mBinding.listOfQualities.setVisibility(View.GONE);
//                                }
//                            });
//                } else {
//                    mBinding.listOfQualities.setVisibility(View.GONE);
//                }
//            }
//
//        });

	}


	/**
	 * 找前置摄像头,没有则返回-1
	 *
	 * @return cameraId
	 */
	private int findFrontFacingCamera() {
		int cameraId = -1;
		//获取摄像头个数
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				cameraFront = true;
				break;
			}
		}
		return cameraId;
	}

	/**
	 * 找后置摄像头,没有则返回-1
	 *
	 * @return cameraId
	 */
	private int findBackFacingCamera() {
		int cameraId = -1;
		//获取摄像头个数
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				cameraId = i;
				cameraFront = false;
				break;
			}
		}
		return cameraId;
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}


	private void releaseMediaRecorder() {
		if (mediaRecorder != null) {
			mediaRecorder.reset();
			mediaRecorder.release();
			mediaRecorder = null;
			if (mCamera != null) {

				mCamera.lock();
			}
		}
	}

	private void initview() {
		Intent intent = getIntent();
		if (intent != null) {
			maxTime = intent.getIntExtra(KEY_TIME, Integer.MAX_VALUE);
		}

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		minute = (TextView) findViewById(R.id.minute);
		second = (TextView) findViewById(R.id.second);
		errorView = (ImageView) findViewById(R.id.errorView);
		startImage = (ImageView) findViewById(R.id.start_camera);
		startImage.setOnClickListener(this);
		changeImage = (ImageView) findViewById(R.id.change_camera);
		changeImage.setOnClickListener(this);
		textChrono = (Chronometer) findViewById(R.id.textChrono);
//        textChrono = new Chronometer(this);
		linearLayout = (LinearLayout) findViewById(R.id.camera_preview);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPreview = new CameraPreview(CamActivity.this, mCamera);
		linearLayout.addView(mPreview);
		linearLayout.setOnTouchListener(this);
	}

	private void stopChronometer() {
//        time = minute + ":" + second;
//        textChrono.stop();

	}

	private void changeRequestedOrientation(int orientation) {
		setRequestedOrientation(orientation);
	}

	private boolean prepareMediaRecorder() {
		mediaRecorder = new MediaRecorder();
		mCamera.unlock();
		mediaRecorder.setCamera(mCamera);
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (cameraFront) {
				mediaRecorder.setOrientationHint(0);
			} else {
				mediaRecorder.setOrientationHint(0);
			}
		}

		mediaRecorder.setProfile(CamcorderProfile.get(quality));
		File file = new File("/mnt/sdcard/videokit");
		if (!file.exists()) {
			file.mkdirs();
		}
		Date d = new Date();
		String timestamp = String.valueOf(d.getTime());
//        url_file = Environment.getExternalStorageDirectory().getPath() + "/videoKit" + timestamp + ".mp4";
		url_file = "/mnt/sdcard/videokit/in.mp4";
//        url_file = "/mnt/sdcard/videokit/" + timestamp + ".mp4";

		File file1 = new File(url_file);
		if (file1.exists()) {
			file1.delete();
		}

		mediaRecorder.setOutputFile(url_file);

//        https://developer.android.com/reference/android/media/MediaRecorder.html#setMaxDuration(int) 不设置则没有限制
//        mediaRecorder.setMaxDuration(150*1000); //设置视频文件最长时间 60s.
//        https://developer.android.com/reference/android/media/MediaRecorder.html#setMaxFileSize(int) 不设置则没有限制
//        mediaRecorder.setMaxFileSize(CameraConfig.MAX_FILE_SIZE_RECORD); //设置视频文件最大size 1G

		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			releaseMediaRecorder();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			releaseMediaRecorder();
			return false;
		}
		return true;

	}

	//计时器
	private void startChronometer() {
		Observable.interval(1, TimeUnit.SECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
					}

					@Override
					public void onNext(Long aLong) {
						alerTime++;
						String minutes = String.valueOf(alerTime / 60);
						if (minutes.length() < 2) {
							minutes = 0 + minutes;
						}
						minute.setText(minutes + ":");
						String seconds = String.valueOf(alerTime % 60);
						if (seconds.length() < 2) {
							seconds = 0 + seconds;
						}
						second.setText(seconds);
						if (alerTime >= maxTime) {
							stop();
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
//        final long startTime = SystemClock.elapsedRealtime();
//        textChrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer arg0) {
//                countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
//                if (countUp % 2 == 0) {
//                } else {
//                }
//                Log.e(TAG, "countUp===>" + countUp);
//                if (countUp >= maxTime) {
//                    stop();
//                }
//                minute.setText(String.format("%02d", countUp / 60) + ":");
//                second.setText(String.format("%02d", countUp % 60));
//            }
//        });
//        textChrono.start();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			//录制
			case R.id.start_camera:
				if ((System.currentTimeMillis() - lastTime) < 1000) {
					return;
				}
				lastTime = System.currentTimeMillis();

				if (orient == 1) {
					Toast.makeText(this, "请横屏再录制", Toast.LENGTH_SHORT).show();
					return;
				}
				if (recording) {
					//如果正在录制点击这个按钮表示录制完成
					stop();
				} else {
					//准备开始录制视频
					if (!prepareMediaRecorder()) {
						Toast.makeText(this, "摄像头初始化失败", Toast.LENGTH_SHORT).show();
//                        setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_FAILED);
						releaseCamera();
						releaseMediaRecorder();
//                        finish();

					}
					//开始录制视频
					runOnUiThread(new Runnable() {
						public void run() {
							// If there are stories, add them to the table
							try {
								mediaRecorder.start();
								isstart = true;
								startImage.setImageResource(R.drawable.video_stop);
								startChronometer();
								if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
									changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
								} else {
									changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
								}
//                                buttonCapture.setImageResource(R.mipmap.player_stop);
							} catch (final Exception ex) {
								Log.i("---", "Exception in thread");
//                                setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_FAILED);
								releaseCamera();
								releaseMediaRecorder();
								finish();
							}
						}
					});
					recording = true;
				}


				break;
			//切换摄像头
			case R.id.change_camera:

				changeCamera();
				break;
			default:
				break;
		}
	}

	private void stop() {
		disposable.dispose();
		if (isstart) {
			mediaRecorder.stop(); //停止
		}
		alerTime = 0;
		startImage.setImageResource(R.drawable.video_start);
		stopChronometer();
//                    mBinding.buttonCapture.setImageResource(R.mipmap.player_record);
//        changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		releaseMediaRecorder();
		Toast.makeText(this, "视频已成功录制", Toast.LENGTH_SHORT).show();
		initPrompt();
		recording = false;
	}

	private void initPrompt() {

		new AlertDialog.Builder(this)
				.setTitle("友情提示")
				.setMessage("是否上传该视频")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialogs = new ProgressDialog(CamActivity.this);
						dialogs.setTitle("正在上传" + "");
						dialogs.setCanceledOnTouchOutside(false);
						dialogs.show();
						initChuan(url_file);
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				})
				.show();

	}

	private void initChuan(String url) {
		File file = uri2File(Uri.parse(url));
		staffFileupload(url);
	}

	private File uri2File(Uri uri) {
		String img_path;
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor actualimagecursor = managedQuery(uri, proj, null,
				null, null);
		if (actualimagecursor == null) {
			img_path = uri.getPath();
		} else {
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			img_path = actualimagecursor
					.getString(actual_image_column_index);
		}
		File file = new File(img_path);
		return file;
	}

	public void staffFileupload(String file) {
//        post_file(HostUtil.kehu + "upload/oss", new HashMap<String, Object>(), file);
		initUpload(file);
	}

	private void initUpload(final String file) {
		String endpoint = mShared.getString("endpoint", "");
		Log.e("endpoint", endpoint);
		OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {
			@Override
			public OSSFederationToken getFederationToken() {
				try {
					URL stsUrl = new URL(HostUtil.host + "/index/ossToken?token=" + mShared.getString("token", ""));
					HttpURLConnection conn = (HttpURLConnection) stsUrl.openConnection();
					InputStream input = conn.getInputStream();
					String jsonText = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME);
					JSONObject jsonObjs = new JSONObject(jsonText);
					JSONObject sts = jsonObjs.getJSONObject("data").getJSONObject("sts");
					String ak = sts.getString("accessKeyId");
					String sk = sts.getString("accessKeySecret");
					String token = sts.getString("accessToken");
					String expiration = sts.getString("expiration");

					return new OSSFederationToken(ak, sk, token, expiration);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		ObjectMetadata metadata = new ObjectMetadata();
// 指定Content-Type
		metadata.setContentType("video/mp4");

		OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
		long l = System.currentTimeMillis();
		String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
		saveDir = mShared.getString("saveDir", "");
		bucket = mShared.getString("bucket", "");
		String object_key = saveDir + "/" + date + "/" + l + ".mp4";
		final String object = date + "/" + l + ".mp4";
		Log.e(TAG, "object" + object_key);
//        loading = new Loading_view(this, R.style.CustomDialog);
//        loading.show();
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest(bucket, object_key, file);
// 异步上传时可以设置进度回调
		put.setMetadata(metadata);
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
			}
		});
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Log.e("PutObject", "UploadSuccess");
				Log.e("ETag", result.getETag());
				Log.e("RequestId", result.getRequestId());
//                loading.dismiss();
				initInfo(object);
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
					Log.e("ErrorCode", serviceException.getErrorCode());
					Log.e("RequestId", serviceException.getRequestId());
					Log.e("HostId", serviceException.getHostId());
					Log.e("RawMessage", serviceException.getRawMessage());
				}
			}
		});
// task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
	}


	protected void post_file(final String url, final Map<String, Object> map, File file) {
		final OkHttpClient client = new OkHttpClient();
		// form 表单形式上传
		MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (file != null) {
			// MediaType.parse() 里面是上传的文件类型。
			RequestBody body = RequestBody.create(MediaType.parse("*/*"), file);
			String filename = file.getName();
			// 参数分别为， 请求key ，文件名称 ， RequestBody
			requestBody.addFormDataPart("file", file.getName(), body);
//            Log.e(TAG, "file:" + file.get);
		}
		if (map != null) {
			// map 里面是请求中所需要的 key 和 value
			for (Map.Entry entry : map.entrySet()) {
				Log.e("MainActivity", "entry.getKey():" + entry.getKey());
				Log.e("MainActivity", "entry.getValue():" + entry.getValue());
				requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
		}
		final Request request = new Request.Builder().url(url).post(requestBody.build()).tag(CamActivity.this).build();
		// readTimeout("请求超时时间" , 时间单位);
		client.newBuilder().readTimeout(50000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.e("lfq", "onFailure" + e.toString());
				client.dispatcher().cancelAll();
				client.connectionPool().evictAll();
				client.newCall(request).enqueue(this);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					String str = response.body().string();
					Log.e("lfq", response.message() + " , body " + str);
					Gson gson = new Gson();
					Video_Upload_Bean video_upload_bean = gson.fromJson(str, Video_Upload_Bean.class);
					if (video_upload_bean.getCode() == 0) {
						ToastUtil.show(CamActivity.this, video_upload_bean.getMsg());
					} else {
						initInfo(video_upload_bean.getData().getObject());
					}

//                    Gson gson = new Gson();
//                    Basic_Head_Bean basic_head_bean = gson.fromJson(str, Basic_Head_Bean.class);
//                    imgUrl = "";
//                    imgUrl = basic_head_bean.getData().getImgUrl();
//                    Log.e(TAG, "imgUrl上传的" + imgUrl);
				} else {
					Log.e("lfq", response.message() + " error : body " + response.body().string());
				}
			}
		});


	}

	private void initInfo(final String object) {
		Map<String, String> map = new HashMap<>();
		map.put("token", mShared.getString("token", ""));
		map.put("fileObject", object);
		Log.e("tianjia", map.toString());
		OkHttp.postAsync(HostUtil.host + "service/addVideo", map, new OkHttp.DataCallBack() {
			@Override
			public void requestFailure(Request request, IOException e) {

			}

			@Override
			public void requestSuccess(String result) throws Exception {
				Log.e("tianjia", TAG + " fewiiw    " + result);
				JSONObject obj = new JSONObject(result);
				int code = obj.getInt("code");
				String msg = obj.getString("msg");

//                    ToastUtil.show(CamActivity.this,msg);
				if (code == 0) {
					ToastUtil.show(CamActivity.this, msg);
				} else {
					ToastUtil.show(CamActivity.this, msg);
					dialogs.dismiss();
					Intent intent = new Intent(CamActivity.this, Person_Video_Activity.class);
					intent.putExtra("video", object);
					intent.putExtra("time", time);
//                  setResult(RESULT_CODE_FOR_RECORD_VIDEO_SUCCEED, intent);
					startActivity(intent);
					releaseCamera();
					releaseMediaRecorder();
					finish();
				}
			}
		});
	}

	/**
	 * 闪光灯
	 */
	private void setFlash() {
		if (!recording && !cameraFront) {
			if (flash) {
				flash = false;
				setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			} else {
				flash = true;
				setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			}
		}
	}

	/**
	 * 切换摄像头
	 */
	private void changeCamera() {
		Log.e(TAG, "hehe" + "走了");
		if (!recording) {
			int camerasNumber = Camera.getNumberOfCameras();
			Log.e(TAG, "hehe" + "走了!recording");
			if (camerasNumber > 1) {
				releaseCamera();
				chooseCamera();
			} else {
				//只有一个摄像头不允许切换
				Toast.makeText(getApplicationContext(), "只有一个摄像头"
						, Toast.LENGTH_SHORT).show();
			}
		}
	}

	//闪光灯
	public void setFlashMode(String mode) {
		try {
			if (getPackageManager().hasSystemFeature(
					PackageManager.FEATURE_CAMERA_FLASH)
					&& mCamera != null
					&& !cameraFront) {

				mPreview.setFlashMode(mode);
				mPreview.refreshCamera(mCamera);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//选择摄像头
	public void chooseCamera() {
		Log.e(TAG, "hehe" + "走了choose");
		if (cameraFront) {
			//当前是前置摄像头
			int cameraId = findBackFacingCamera();
			if (cameraId >= 0) {
				// open the backFacingCamera
				// set a picture callback
				// refresh the preview
				mCamera = Camera.open(cameraId);
				// mPicture = getPictureCallback();
				mPreview.refreshCamera(mCamera);
				reloadQualities(cameraId);
			}
		} else {
			if (isQianzhi) {
				return;
			}
			//当前为后置摄像头
			int cameraId = findFrontFacingCamera();
			if (cameraId >= 0) {
				// open the backFacingCamera
				// set a picture callback
				// refresh the preview
				mCamera = Camera.open(cameraId);
				if (flash) {
					flash = false;
//                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_off_white);
					mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				}
				// mPicture = getPictureCallback();
				mPreview.refreshCamera(mCamera);
				reloadQualities(cameraId);
			}
		}
	}

	//检查设备是否有摄像头
	private boolean hasCamera(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {


		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			try {
				focusOnTouch(event);
			} catch (Exception e) {
				Log.i(TAG, "对焦失败:%1$s");
				//do nothing
			}
		}
		return true;
	}

	/**
	 * 对焦
	 *
	 * @param event
	 */
	private void focusOnTouch(MotionEvent event) {
		if (mCamera != null) {
			Camera.Parameters parameters = mCamera.getParameters();
			if (parameters.getMaxNumMeteringAreas() > 0) {
				Rect rect = calculateFocusArea(event.getX(), event.getY());
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
				meteringAreas.add(new Camera.Area(rect, 800));
				parameters.setFocusAreas(meteringAreas);
				mCamera.setParameters(parameters);
				mCamera.autoFocus(mAutoFocusTakePictureCallback);
			} else {
				mCamera.autoFocus(mAutoFocusTakePictureCallback);
			}
		}
	}

	private Rect calculateFocusArea(float x, float y) {
		int left = clamp(Float.valueOf((x / mPreview.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
		int top = clamp(Float.valueOf((y / mPreview.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
		return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
	}

	private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
		int result;
		if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
			if (touchCoordinateInCameraReper > 0) {
				result = 1000 - focusAreaSize / 2;
			} else {
				result = -1000 + focusAreaSize / 2;
			}
		} else {
			result = touchCoordinateInCameraReper - focusAreaSize / 2;
		}
		return result;
	}

	private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				// do something...
				Log.i("tap_to_focus", "success!");
			} else {
				// do something...
				Log.i("tap_to_focus", "fail!");
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_FOR_PERMISSIONS) {
			//权限申请
			if (PermissionsActivity.PERMISSIONS_DENIED == resultCode) {
				//权限未被授予，退出应用
				finish();
			} else if (PermissionsActivity.PERMISSIONS_GRANTED == resultCode) {
				//权限被授予
				//do nothing
			}
		}
	}

	/**
	 * 0 为横屏
	 * 1 为竖屏
	 *
	 * @param orient
	 */
	@Override
	public void onOrientation(int orient) {
		this.orient = orient;
		Log.e("test", "走了");
		if (orient == 0) {
			errorView.setVisibility(View.GONE);
			horizontalStart();
		} else {
			errorView.setVisibility(View.VISIBLE);
			verticalPause();
		}
	}

	private void horizontalStart() {
		if (alerTime != 0) {
			alerTime = maxTime - alerTime;
		}
		Observable.interval(1, TimeUnit.SECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
					}

					@Override
					public void onNext(Long aLong) {
						alerTime++;
						String minutes = String.valueOf(alerTime / 60);
						if (minutes.length() < 2) {
							minutes = 0 + minutes;
						}
						minute.setText(minutes + ":");
						String seconds = String.valueOf(alerTime % 60);
						if (seconds.length() < 2) {
							seconds = 0 + seconds;
						}
						second.setText(seconds);
						if (alerTime >= maxTime) {
							stop();
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
		if (mediaRecorder != null) {
			mediaRecorder.start();
		}
	}

	private void verticalPause() {
		if (disposable != null) {
			disposable.dispose();
			if (mediaRecorder != null) {
				mediaRecorder.pause();
			}
		}

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (recording) {
			//如果正在录制点击这个按钮表示录制完成
			stop();
		}

	}

}
