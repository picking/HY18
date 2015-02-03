package com.hmhelper.adapter;

import com.hmhelper.fragment.Fragment_answer;
import com.hmhelper.fragment.Fragment_healthknowledge;
import com.hmhelper.fragment.Fragment_healthmessage;
import com.hmhelper.setting.Constant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CulturalTabPageIndicatorAdapter extends FragmentPagerAdapter {

	private String[] TITLE;
	private Fragment mFm = null;

	public CulturalTabPageIndicatorAdapter(FragmentManager fm, String[] TITLE) {
		super(fm);
		this.TITLE = TITLE;
	}

	@Override
	public Fragment getItem(int position) {

		if (Constant.culturalCacheFragments.containsKey(position)) {
			mFm = Constant.culturalCacheFragments.get(position);
		} else {
			switch (position) {
			case 0:
				mFm = Fragment_healthknowledge.newInstance();
				break;

			case 1:
				mFm = Fragment_answer.newInstance();
				break;

			default:
				mFm = Fragment_healthmessage.newInstance();
				break;
			}
			Constant.culturalCacheFragments.put(position, mFm);
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
