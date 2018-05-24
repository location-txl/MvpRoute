package com.location.mvp.mvp_route_demo.view.fragment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.contract.TestContract;
import com.location.mvp.mvp_route_demo.presenter.TestPresenter;
import com.location.mvp.mvproutelibrary.Base.BaseFragment;
import com.location.mvp.mvproutelibrary.Base.BaseThrowable;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 19:07
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class TestFragment extends BaseFragment<TestContract.Presenter> implements TestContract.View {
    public void ss() {
        Log.e("TEST", "dsa");
        presenter.ss();
    }

    @Override
    protected void initView(View view) {

        view.findViewById(R.id.fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
presenter.ss();

            }
        });
    }

    @Override
    protected void loadData() {


    }

    @NonNull
    @Override
    protected TestContract.Presenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_one;
    }

    @Override
    public void load() {
        Toast.makeText(getActivity(), "你好啊", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onshowError(BaseThrowable baseThrowable) {
        Toast.makeText(getActivity(), baseThrowable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
