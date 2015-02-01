package com.hmhelper.fragment;

import com.hmhelper.activity.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View contextView = inflater.inflate(R.layout.fragment_item, container, false);
		TextView mTextView = (TextView) contextView.findViewById(R.id.textview);

		// 获取Activity传递过来的参数
		Bundle mBundle = getArguments();
		String title = mBundle.getString("arg");

		mTextView.setText(title);

		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}