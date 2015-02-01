package com.hmhelper.adapter;

import com.hmhelper.fragment.Fragment_newfocus;
import com.hmhelper.fragment.ItemFragment;
import com.hmhelper.setting.Constant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsTabPageIndicatorAdapter extends FragmentPagerAdapter {

	private String[] TITLE;
	private Fragment mFm = null;

	public NewsTabPageIndicatorAdapter(FragmentManager fm, String[] TITLE) {
		super(fm);
		this.TITLE = TITLE;
	}

	@Override
	public Fragment getItem(int position) {

		if (Constant.newsCacheFragments.containsKey(position)) {
			mFm = Constant.newsCacheFragments.get(position);
		} else {
			switch (position) {
			case 0:
				mFm = Fragment_newfocus.newInstance();
				break;

			default:
				mFm = new ItemFragment();
				Bundle args = new Bundle();
				args.putString("arg", TITLE[position]);
				mFm.setArguments(args);
				break;
			}
			Constant.newsCacheFragments.put(position, mFm);
		}
		return mFm;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLE[position % TITLE.length];
	}

	@Override
	public int getCount() {
		return TITLE.length;
	}
}
