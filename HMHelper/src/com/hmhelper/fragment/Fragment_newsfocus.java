package com.hmhelper.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.hmhelper.activity.NewsFocusDetialActivity;
import com.hmhelper.activity.R;
import com.hmhelper.adapter.NewsFocusAdapter;
import com.hmhelper.async.SimpleTask;
import com.hmhelper.async.TaskExecutor;
import com.hmhelper.entity.ListNewsFocus;
import com.hmhelper.entity.NewsFocusCollection;
import com.hmhelper.recall.BaseRequestRecall;
import com.hmhelper.setting.CacheSetting;
import com.hmhelper.setting.Constant;
import com.hmhelper.utils.AnimationUtil;
import com.hmhelper.utils.HttpDataUtil;
import com.hmhelper.utils.NetWorkUtils;
import com.hmhelper.utils.StringUtils;
import com.hmhelper.view.refresh.PullToRefreshBase;
import com.hmhelper.view.refresh.PullToRefreshBase.OnRefreshListener;
import com.hmhelper.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment_newsfocus extends BaseFragment {

	@ViewInject(R.id.pulllv_newsfocus)
	private PullToRefreshListView pulllv_newsfocus;

	private ListView lview;
	private NewsFocusAdapter newsfadapter;
	private ListNewsFocus lFocus = new ListNewsFocus();
	private List<NameValuePair> parameters;
	private NewsFocusCollection listcln;
	private Intent intent;

	public static Fragment_newsfocus newInstance() {
		Fragment_newsfocus fragment = new Fragment_newsfocus();
		return fragment;
	}

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		super.onCreateView(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.fragment_newsfocus,
				getViewGroup(), false);
		ViewUtils.inject(this, view);
		setContentView(view);

		initView();
	}

	private void initView() {
		initPull();
		String oldnewsFocus = CacheSetting.instance().getAsString("NewsFocus");
		if (StringUtils.isNotEmpty(oldnewsFocus)) {
			listcln = JSON.parseObject(oldnewsFocus, NewsFocusCollection.class);
			lFocus.setLnews(listcln.getYi18());
		} else {
			pulllv_newsfocus.doPullRefreshing(true, 500);
		}
		newsfadapter = new NewsFocusAdapter(getApplicationContext(), lFocus);
		lview.setAdapter(newsfadapter);
		lview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getApplicationContext(),
						NewsFocusDetialActivity.class);
				intent.putExtra("NEWSFOCUSID", lFocus.getLnews().get(position)
						.getId());
				AnimationUtil.startActivity(getActivity(), intent);
			}
		});
	}

	private void initPull() {
		pulllv_newsfocus.setPullLoadEnabled(false);
		pulllv_newsfocus.setScrollLoadEnabled(true);
		pulllv_newsfocus
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pullRefresh();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pullMore();
					}

				});
		lview = pulllv_newsfocus.getRefreshableView();
		lview.setClipToPadding(false);
		lview.setDivider(null);
		lview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
	}

	private void pullRefresh() {
		if (NetWorkUtils.isConnect(getApplicationContext())) {
			System.gc();
			TaskExecutor.newOrderedExecutor().put(getNewsFocusData(true))
					.put(doneRefresh()).start();
		} else {
			// showNetWorkToast();
			refreshset();
		}
	}

	private void pullMore() {
		if (NetWorkUtils.isConnect(getApplicationContext())) {
			System.gc();
			TaskExecutor.newOrderedExecutor().put(getNewsFocusData(false))
					.put(doneMore()).start();
			refreshset();
		} else {
			// showNetWorkToast();
			moreset();
		}
	}

	private SimpleTask<?> getNewsFocusData(final boolean flag) {
		SimpleTask<?> listTask = new SimpleTask<Map<String, Object>>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				parameters = new ArrayList<>();
				if (flag) {
					lFocus.setPage(1);
				} else {
					lFocus.setPage(lFocus.getPage() + 1);
				}
				parameters.add(new BasicNameValuePair("page", lFocus.getPage()
						+ ""));
				parameters.add(new BasicNameValuePair("limit",
						Constant.limitCount + ""));
			}

			@Override
			protected Map<String, Object> doInBackground() {
				return HttpDataUtil.executeHttp(getApplicationContext(),
						parameters, R.string.url_newsfocus_list);
			}

			@Override
			protected void onPostExecute(Map<String, Object> result) {
				super.onPostExecute(result);
				HttpDataUtil.requestBackHandle(result, new BaseRequestRecall() {

					@Override
					public void resultSuccess(Object msg) {
						listcln = JSON.parseObject(msg.toString(),
								NewsFocusCollection.class);
						if (listcln.getYi18() != null) {
							if (flag) {
								lFocus.setLnews(listcln.getYi18());
								CacheSetting.instance().put("NewsFocus",
										msg.toString());
							} else {
								lFocus.getLnews().addAll(listcln.getYi18());
							}
							if (listcln.getYi18().size() < Constant.limitCount) {
								lFocus.setHasmore(false);
							} else {
								lFocus.setHasmore(true);
							}
						}
						newsfadapter.notifyDataSetChanged();
					}

					@Override
					public void resultFailed(Object msg) {
						lFocus.setPage(lFocus.getPage() - 1);
						showToast(msg.toString());
					}

					@Override
					public void requestFailed(Object msg) {
						lFocus.setPage(lFocus.getPage() - 1);
						showToast(msg.toString());
					}
				});
			}
		};
		return listTask;
	}

	private SimpleTask<?> doneRefresh() {
		SimpleTask<?> donerefreshTask = new SimpleTask<String>() {

			@Override
			protected String doInBackground() {
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				refreshset();
			}

		};
		return donerefreshTask;
	}

	private void refreshset() {
		pulllv_newsfocus.onPullDownRefreshComplete();
		pulllv_newsfocus.setHasMoreData(lFocus.isHasmore());
	}

	private SimpleTask<?> doneMore() {
		SimpleTask<?> donemoreTask = new SimpleTask<String>() {

			@Override
			protected String doInBackground() {
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				moreset();
			}

		};
		return donemoreTask;
	}

	private void moreset() {
		pulllv_newsfocus.onPullUpRefreshComplete();
		pulllv_newsfocus.setHasMoreData(lFocus.isHasmore());
	}
}
