package com.location.mvp.mvp_route_demo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.location.mvp.mvp_route_demo.R;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/20 17:33
 * description：
 */

public class OneFragment extends Fragment {
	private TextView textView;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_text, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		textView = view.findViewById(R.id.fragment_text);
		Bundle arguments = getArguments();
		String string = arguments.getString("123");
		textView.setText(string);
	}
}
