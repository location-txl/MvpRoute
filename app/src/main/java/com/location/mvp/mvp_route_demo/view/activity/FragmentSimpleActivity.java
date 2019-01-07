package com.location.mvp.mvp_route_demo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioGroup;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.base.BaseToActivity;
import com.location.mvp.mvp_route_demo.view.fragment.SimpleFragment;
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.FragmentUtils;

/**
 * 项目:MvpRoute
 * link:https://github.com/TLocation/MvpRoute
 * @ClassName FragmentSimpleActivity
 * @Author tianxiaolong
 * @Date 2019/1/6 17:47
 */
@Layout(R.layout.activity_fragment_simple)
public class FragmentSimpleActivity extends BaseToActivity {
	private RadioGroup radioGroup;

	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected String getTooBarTitle() {
		return "Fragment管理 完成app首页样式";
	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {
		super.initView(savedInstanceState);
		radioGroup = findViewById(R.id.main_group);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.radio_1:
						FragmentUtils.getInstance(activity).start(SimpleFragment.class, "home")
								.add(R.id.main_content)
								.setBundle(getBundle("首页"))
								.commit();
						break;
					case R.id.radio_2:
						FragmentUtils.getInstance(activity).start(SimpleFragment.class, "market")
								.add(R.id.main_content)
								.setBundle(getBundle("市场"))
								.commit();
						break;
					case R.id.radio_3:
						FragmentUtils.getInstance(activity).start(SimpleFragment.class, "found")
								.add(R.id.main_content)
								.setBundle(getBundle("发现"))
								.commit();
						break;
					case R.id.radio_4:
						FragmentUtils.getInstance(activity).start(SimpleFragment.class, "My")
								.add(R.id.main_content)
								.setBundle(getBundle("我的"))
								.commit();
						break;
				}
			}
		});
		FragmentUtils.getInstance(this).start(SimpleFragment.class, "home")
				.add(R.id.main_content)
				.setBundle(getBundle("首页"))
				.commit();
	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	private Bundle getBundle(String text) {
		Bundle bundle = new Bundle();
		bundle.putString(SimpleFragment.EXERA_CONTENT, text);
		return bundle;
	}
}
