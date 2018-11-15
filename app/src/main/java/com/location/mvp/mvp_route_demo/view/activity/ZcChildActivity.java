package com.location.mvp.mvp_route_demo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.bean.ZcChildBean;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import java.util.ArrayList;
import java.util.List;

import static com.location.mvp.mvp_route_demo.view.activity.ZcActivity.EXTRA_DATA;
import static com.location.mvp.mvp_route_demo.view.activity.ZcActivity.EXTRA_POSITION;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/2 20:19
 * description：
 */
@Layout(R.layout.activity_home)
public class ZcChildActivity extends BaseToActivity implements View.OnClickListener, OnItemClickListener {

	private List<ZcChildBean> selectList;
	private static final String data = "das";
	private static final String dataPOS = "das_pos";
	private List<ZcChildBean> zcChildBeans;
	private int position;

	public static Intent start(Context context, ArrayList<ZcChildBean> childBeans,int position) {
		Intent intent = new Intent(context, ZcChildActivity.class);
		intent.putParcelableArrayListExtra(data, childBeans);
		intent.putExtra(dataPOS,position);
		return intent;
	}
	public static Intent start(Context context, ArrayList<ZcChildBean> childBeans) {
		Intent intent = new Intent(context, ZcChildActivity.class);
		intent.putParcelableArrayListExtra(data, childBeans);
		intent.putExtra(dataPOS,-1);
		return intent;
	}
	@Override
	public void onshowError(ExceptionHandle.ResponseThrowable baseThrowable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "请选择属性";
	}


	@Override
	protected void loadData() {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		TextView rightText = findViewById(R.id.toobar_right_text);
		rightText.setVisibility(View.VISIBLE);
		rightText.setOnClickListener(this);
		RecyclerView recyclerView = findViewById(R.id.home_RecyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		List<Integer> select = getSelect();
		zcChildBeans = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			ZcChildBean zcChildBean = new ZcChildBean();
			zcChildBean.setId(i);
			zcChildBean.setChildTitle("标签" + i);
			if (select.contains(i)) {
				zcChildBean.setSelect(true);
			} else {
				zcChildBean.setSelect(false);
			}
			zcChildBeans.add(zcChildBean);
		}
		recyclerView.setAdapter(new BaseAdapter<ZcChildBean,BaseViewHolder>(zcChildBeans, R.layout.item_child_select, this) {
			@Override
			public void conver(BaseViewHolder holder, @Nullable ZcChildBean data, int viewType) {
				TextView textView = holder.findViewById(R.id.select_text);
				if(data.isSelect()){
					textView.setTextColor(Color.RED);
				}else{
					textView.setTextColor(Color.BLACK);
				}
				textView.setText(data.getChildTitle());
			}


		});
	}

	/**
	 *  选择过的所有id都保存在集合里面，在网络请求的数据里面进行吧判断如果包含这个id就是选择过的状态
	 * @return
	 */
	private List<Integer> getSelect() {
		List<Integer> list = new ArrayList<>();
		Intent intent = getIntent();
		position = intent.getIntExtra(dataPOS, -1);
		ArrayList<ZcChildBean> selectList = intent.getParcelableArrayListExtra(data);
		for (ZcChildBean zcChildBean : selectList) {
			list.add(zcChildBean.getId());
		}
		return list;
	}

	@NonNull
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(BaseViewHolder viewHolder, View view, int position) {
		ZcChildBean zcChildBean = zcChildBeans.get(position);
		if(this.position==-1){
			finish();
		}
		// 如果是选择过的就return，否则把数据传递过去，在把当前的索引传递过去
		if(zcChildBean.isSelect()){
			return;
		}
		Intent intent = new Intent();
		intent.putExtra(EXTRA_POSITION,this.position);
		intent.putExtra(EXTRA_DATA,zcChildBean);
		setResult(RESULT_OK,intent);
		finish();
	}
}
