package com.hmhelper.activity;

import java.util.ArrayList;
import java.util.List;

import com.hmhelper.adapter.DrawerExpandAdapter;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.Item;
import com.hmhelper.fragment.Fragment_cultural;
import com.hmhelper.fragment.Fragment_news;
import com.hmhelper.fragment.Fragment_none;
import com.hmhelper.view.ExpandableListViewForScrollView;
import com.hmhelper.view.lefdrewer.ActionBarDrawerToggle;
import com.hmhelper.view.lefdrewer.DrawerArrowDrawable;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;

@SuppressLint({ "ResourceAsColor", "NewApi" })
public class MainActivity extends BaseActivity implements OnChildClickListener {

	@ViewInject(R.id.drawer_layout)
	private DrawerLayout mDrawerLayout;
	@ViewInject(R.id.ll_content)
	private LinearLayout ll_content;
	@ViewInject(R.id.ll_drawer)
	private LinearLayout ll_drawer;
	@ViewInject(R.id.expand_list)
	private ExpandableListViewForScrollView expan_list;
	private DrawerExpandAdapter mAdapter = null;

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private List<List<Item>> mData = new ArrayList<List<Item>>();

	private FragmentManager fmanger = null;
	private FragmentTransaction trans = null;
	private Fragment frag = null;

	private int[] mGroupArrays = new int[] { R.array.tianlongbabu,
			R.array.shediaoyingxiongzhuan, R.array.shendiaoxialv };

	private int[] mDetailIds = new int[] { R.array.tianlongbabu_detail,
			R.array.shediaoyingxiongzhuan_detail, R.array.shendiaoxialv_detail };

	private int[][] mImageIds = new int[][] {
			{ R.drawable.img11, R.drawable.img11, R.drawable.img11 },
			{ R.drawable.img11, R.drawable.img11, R.drawable.img11,
					R.drawable.img11, R.drawable.img11, R.drawable.img11,
					R.drawable.img11 }, { R.drawable.img11, R.drawable.img11 } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		HMaplication.instance.addActivity(this);
		ViewUtils.inject(this);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		setDefaultFragment();

		initData();

		mAdapter = new DrawerExpandAdapter(this, mData);
		expan_list.setAdapter(mAdapter);
		expan_list
				.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
		expan_list.setOnChildClickListener(this);
	}

	private void initData() {
		for (int i = 0; i < mGroupArrays.length; i++) {
			List<Item> list = new ArrayList<Item>();
			String[] childs = getStringArray(mGroupArrays[i]);
			String[] details = getStringArray(mDetailIds[i]);
			for (int j = 0; j < childs.length; j++) {
				Item item = new Item(mImageIds[i][j], childs[j], details[j]);
				list.add(item);
			}
			mData.add(list);
		}
	}

	private String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	private void setDefaultFragment() {
		fmanger = getSupportFragmentManager();
		trans = fmanger.beginTransaction();
		frag = new Fragment_news();
		trans.replace(R.id.ll_content, frag);
		trans.commit();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		if (groupPosition == 0) {
			if (childPosition == 0) {
				fmanger = getSupportFragmentManager();
				trans = fmanger.beginTransaction();
				frag = new Fragment_news();
				trans.replace(R.id.ll_content, frag);
				trans.commit();
			} else if (childPosition == 1) {
				fmanger = getSupportFragmentManager();
				trans = fmanger.beginTransaction();
				frag = new Fragment_cultural();
				trans.replace(R.id.ll_content, frag);
				trans.commit();
			} else if (childPosition == 2) {
				fmanger = getSupportFragmentManager();
				trans = fmanger.beginTransaction();
				frag = new Fragment_none();
				trans.replace(R.id.ll_content, frag);
				trans.commit();
			}
			drawerToggle();
		} else {
			Item item = mAdapter.getChild(groupPosition, childPosition);
			new AlertDialog.Builder(this)
					.setTitle(item.getName())
					.setMessage(item.getDetail())
					.setIcon(android.R.drawable.ic_menu_more)
					.setNegativeButton(android.R.string.cancel,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).create().show();
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(ll_drawer)) {
				mDrawerLayout.closeDrawer(ll_drawer);
			} else {
				mDrawerLayout.openDrawer(ll_drawer);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void drawerToggle() {
		if (mDrawerLayout.isDrawerOpen(ll_drawer)) {
			mDrawerLayout.closeDrawer(ll_drawer);
		} else {
			mDrawerLayout.openDrawer(ll_drawer);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
