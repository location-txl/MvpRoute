package com.location.mvp.mvp_route_demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.location.mvp.mvproutelibrary.adapter.OnItemClickListener;
import com.location.mvp.mvproutelibrary.adapter.ViewHolder;
import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/28 21:56
 * description：
 */

public class TestActivity extends AppCompatActivity implements OnItemClickListener {

	public static final String str = "{\n" +
			"    \"result\": {\n" +
			"        \"unitList\": [\n" +
			"            {\n" +
			"                \"unitId\": 1,\n" +
			"                \"unitName\": \"Unit 1 School\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            {\n" +
			"                \"unitId\": 2,\n" +
			"                \"unitName\": \"Unit 2 Face\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            {\n" +
			"                \"unitId\": 3,\n" +
			"                \"unitName\": \"Unit 3 Animals\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            {\n" +
			"                \"unitId\": 4,\n" +
			"                \"unitName\": \"Unit 4 Numbers\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            {\n" +
			"                \"unitId\": 5,\n" +
			"                \"unitName\": \"Unit 5 Colours\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            {\n" +
			"                \"unitId\": 6,\n" +
			"                \"unitName\": \"Unit 6 Fruit\",\n" +
			"                \"lessonList\": [\n" +
			"                    {\n" +
			"                        \"lessonId\": 1,\n" +
			"                        \"lessonName\": \"Lesson 1\"\n" +
			"                    },\n" +
			"                    {\n" +
			"                        \"lessonId\": 2,\n" +
			"                        \"lessonName\": \"Lesson 2\"\n" +
			"                    }\n" +
			"                ]\n" +
			"            }\n" +
			"        ]\n" +
			"    },\n" +
			"    \"error\": \"\",\n" +
			"    \"code\": 200\n" +
			"}";
	private TestAdapter testAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_ps);
		RecyclerView recyclerView = findViewById(R.id.my_rect);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		testAdapter = new TestAdapter(R.layout.item_title);
		testAdapter.addType(1, R.layout.item_title);
		testAdapter.addType(2, R.layout.item_content);
		testAdapter.setOnItemClickListener(this);
		recyclerView.setAdapter(testAdapter);
		Gson gson = new Gson();
		TestBean testBean = gson.fromJson(str, TestBean.class);

		final List<OerBean> list = new ArrayList<>();


	}

	private View lastView;

	@Override
	public void onItemClick(ViewHolder viewHolder, View view, int position) {
		if (testAdapter.getItemViewType(position) == 1) {
			return;
		}
		if (view == lastView) {
			return;
		}
		if (lastView != null) {
			TextView textView = lastView.findViewById(R.id.item_content);
			textView.setTextColor(Color.BLACK);
		}
		TextView content = view.findViewById(R.id.item_content);
		content.setTextColor(Color.BLUE);
		lastView = view;


	}
}
