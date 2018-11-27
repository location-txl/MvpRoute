package ${packageName}.view;

import android.support.annotation.Nullable;
import android.os.Bundle;

import ${packageName}.contract.${contarctName};
import com.location.mvp.mvproutelibrary.base.BaseActivity;
import com.location.mvp.mvproutelibrary.base.BasePresenter;
import com.location.mvp.mvproutelibrary.base.Layout;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;

@Layout(R.layout.${layoutName})
public class ${activityClass} extends BaseActivity<${contarctName}.Presenter> implements ${contarctName}.View{

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
	protected ${contarctName}.Presenter createPresenter() {
		return new ${presenterName}();
	}
}
