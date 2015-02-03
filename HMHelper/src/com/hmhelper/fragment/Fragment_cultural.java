package com.hmhelper.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmhelper.activity.R;
import com.hmhelper.adapter.CulturalTabPageIndicatorAdapter;
import com.hmhelper.view.indicator.TabPageIndicator;

public class Fragment_cultural extends BaseFragment {

	private static final String[] TITLE = new String[] { "健康知识", "健康一问", "健康图书" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_cultural,
				container, false);
		// ViewPager的adapter
		FragmentPagerAdapter tabadapter = new CulturalTabPageIndicatorAdapter(
				getChildFragmentManager(), TITLE);
		ViewPager pager = (ViewPager) contextView
				.findViewById(R.id.cultural_pager);
		pager.setAdapter(tabadapter);

		// 实例化TabPageIndicator然后设置ViewPager与之关联
		TabPageIndicator indicator = (TabPageIndicator) contextView
				.findViewById(R.id.cultural_indicator);
		indicator.setViewPager(pager);

		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// Toast.makeText(getActivity(), TITLE[arg0],
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
