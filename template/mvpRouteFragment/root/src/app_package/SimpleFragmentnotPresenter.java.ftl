package ${packageName}.view;

import android.support.annotation.Nullable;
import android.os.Bundle;

import com.location.mvp.mvproutelibrary.base.BaseFragment;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

@Layout(R.layout.${layoutName})
public class ${activityClass} extends BaseFragment {

 	@Override
	public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

	}

	@Override
	protected void initView(@Nullable Bundle savedInstanceState) {

	}

	@Override
	protected void loadData() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}
}
