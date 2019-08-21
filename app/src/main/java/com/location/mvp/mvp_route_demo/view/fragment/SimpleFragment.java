package com.location.mvp.mvp_route_demo.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvproutelibrary.base.BaseFragment;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.InjectBundle;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.scheduler.RxScheduler;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;

/**
 * 项目:MvpRoute
 * link:https://github.com/TLocation/MvpRoute
 * @ClassName SimpleFragment
 * @Author tianxiaolong
 * @Date 2019/1/6 17:54
 */
@Layout(R.layout.fragment_simple)
public class SimpleFragment extends BaseFragment {
	public static final String EXERA_CONTENT = "simpleFragment_content";

	private TextView content;
	@InjectBundle(EXERA_CONTENT)
	private String title;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		content = (TextView) findViewById(R.id.fragment_text);
		RxScheduler.click(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(content.getText().toString().equals("首页")){
//					startActivity(new Intent(activity, FragmentSimpleActivity.class));
					Bundle bundle = new Bundle();
					bundle.putString(SimpleFragment.EXERA_CONTENT, "回退");
					FragmentUtils.getInstance(activity)
							.start(SimpleFragment.class,"test")
							.add(R.id.main_content)
							.setBundle(bundle)
							.addToBack()
							.commit();
				}else if("回退".equals(content.getText().toString())){
					AlertDialog alertDialog = new AlertDialog.Builder(activity)
							.setTitle("标题")
							.setMessage("内容")
							.create();
					alertDialog.show();
				}
			}
		},content);
	}

	@Override
	protected void loadData() {
		content.setText(title);
	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}
}
