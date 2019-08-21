package com.location.mvp.mvp_route_demo.view.activity.adapteractivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvproutelibrary.adapter.AdapterList;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;
import com.location.mvp.mvproutelibrary.adapter.SelectAdapter;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

import java.util.Collection;
import java.util.List;

/**
 * @author tianxiaolong
 * time：19-4-28 下午10:50
 * description：
 */
@Layout(R.layout.activity_single_adapter)
public class SingleAdapterActivity extends BaseToActivity {
	@Override
	protected String getTooBarTitle() {
		return "单选适配器";
	}

	@Override
	protected void  loadData() {


	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		RecyclerView recyclerView = findViewById(R.id.single_recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		List<String> list = new AdapterList<>();
		for(int i=0;i<200;i++){
			list.add("测试" + i);
		}
		Adapter adapter = new Adapter(list, R.layout.item_data);
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}
	class Adapter extends SelectAdapter<String, BaseViewHolder> {

		public Adapter(Collection<String> data, int layout) {
			super(data, layout,TYPE_MULTIPLE);
		}

		@Override
		protected void onCheckChange(BaseViewHolder holder, String data, int viewType, boolean checked) {
			Log.d(TAG, "onCheckChange position:" + holder.getAdapterPosition() + "\t\tchecked:" + checked);
			if(checked){
				holder.getItemView().setBackgroundColor(Color.RED);
			}else{
				holder.getItemView().setBackgroundColor(Color.WHITE);
			}
		}

		@Override
		protected void onConverData(BaseViewHolder holder, @Nullable String data, int viewType) {
			holder.setText(R.id.item_title, data);
			Log.d(TAG, "onConverData position:" + holder.getAdapterPosition());
		}
	}
}
