package com.location.mvp.mvp_route_demo.view.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.location.mvp.mvp_route_demo.R;
import com.location.mvp.mvp_route_demo.adapter.HomeAdapter;
import com.location.mvp.mvproutelibrary.Base.BaseActivity;
import com.location.mvp.mvproutelibrary.Base.BasePresenter;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.utils.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/7/21 0021 16:46
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class HomeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @Override
    public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.home_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST, 5, Color.parseColor("#999999")));

        homeAdapter = new HomeAdapter(R.layout.item_home);
        recyclerView.setAdapter(homeAdapter);

    }

    @Override
    protected void loadData() {
        String[] stringArray = getResources().getStringArray(R.array.home_list);
        List<String> list = Arrays.asList(stringArray);
        homeAdapter.refresh(list);
    }

    @NonNull
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}
