package com.location.mvp.mvp_route_demo.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.bean.FoorData;
import com.location.mvp.mvproutelibrary.adapter.BaseAdapter;
import com.location.mvp.mvproutelibrary.adapter.BaseViewHolder;
import com.location.mvp.mvproutelibrary.adapter.OnHeaderClickListener;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.SpanUtils;
import com.location.mvp.mvproutelibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianxiaolong
 *         time：2019/1/11 23:15
 *         description：
 */
@Layout(R.layout.activity_foor)
public class FoorActivity extends BaseActivity {
	private RecyclerView recyclerView;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		recyclerView = findViewById(R.id.id_recy);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MyFoolAdapter myFoolAdapter = new MyFoolAdapter(R.layout.item_footer);
		myFoolAdapter.addFooterView(R.layout.view_footer_fool);
		List<FoorData> list = new ArrayList<>();
		List<FoorData> tempData = new ArrayList<>();
		list.add(new FoorData("小明", "小红", "我爱你"));
		list.add(new FoorData("小红", "小明", "我不接受你的爱"));
		tempData.add(new FoorData("小样", "小明", "我才爱你"));
		tempData.add(new FoorData("小红", "小样", "你别抢小明"));
		myFoolAdapter.setData(list);
		recyclerView.setAdapter(myFoolAdapter);
		myFoolAdapter.setOnHeaderClickListener(new OnHeaderClickListener() {
			@Override
			public void onHeaderClick(int layout, View view, @Nullable Object data, int position, boolean isHeader) {
				myFoolAdapter.loadItem(tempData);
			}
		});
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	class MyFoolAdapter extends BaseAdapter<FoorData, BaseViewHolder> {

		public MyFoolAdapter(int layout) {
			super(layout);
		}

		@Override
		public void conver(BaseViewHolder holder, @Nullable FoorData data, int viewType) {
			TextView viewById = holder.findViewById(R.id.item_user);
			viewById.setMovementMethod(LinkMovementMethod.getInstance());
			String message = data.getFromName() + "对" + data.getToName() + "说:";
			CharSequence build = SpanUtils.Builder(message)
					.setBorder(0, data.getFromName().length())
					.setTextColor(Color.RED)
					.setClick(new ClickableSpan() {
						@Override
						public void onClick(View widget) {
							ToastUtils.showShort("点击发送者");
						}
					})
					.setBorder(data.getFromName().length() + 1, data.getFromName().length() + 1 + data.getToName().length())
					.setTextColor(Color.BLUE)
					.setClick(new ClickableSpan() {
						@Override
						public void onClick(View widget) {
							ToastUtils.showShort("点击接收者");
						}
					}).build();
			holder.setText(R.id.item_user, build);
			holder.setText(R.id.item_message, data.getMessage());
		}
	}
}
