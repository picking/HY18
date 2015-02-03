package com.hmhelper.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.hmhelper.activity.AnswerDetialActivity;
import com.hmhelper.activity.R;
import com.hmhelper.adapter.AnswerAdapter;
import com.hmhelper.async.SimpleTask;
import com.hmhelper.async.TaskExecutor;
import com.hmhelper.entity.ListQuestion;
import com.hmhelper.entity.QuestionCollection;
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

public class Fragment_answer extends BaseFragment {

	@ViewInject(R.id.pulllv_newsfocus)
	private PullToRefreshListView pulllv_newsfocus;

	private ListView lview;
	private AnswerAdapter anadapter;
	private ListQuestion lque = new ListQuestion();
	private List<NameValuePair> parameters;
	private QuestionCollection listcln;
	private Intent intent;

	public static Fragment_answer newInstance() {
		Fragment_answer fragment = new Fragment_answer();
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
		String oldnewsFocus = CacheSetting.instance().getAsString("Question");
		if (StringUtils.isNotEmpty(oldnewsFocus)) {
			listcln = JSON.parseObject(oldnewsFocus, QuestionCollection.class);
			lque.setLque(listcln.getYi18());
		} else {
			pulllv_newsfocus.doPullRefreshing(true, 500);
		}
		anadapter = new AnswerAdapter(getApplicationContext(), lque);
		lview.setAdapter(anadapter);
		lview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getApplicationContext(),
						AnswerDetialActivity.class);
				intent.putExtra("QUESTIONID", lque.getLque().get(position)
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
		pulllv_newsfocus.footerRefreshSetting();
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
					lque.setPage(1);
				} else {
					lque.setPage(lque.getPage() + 1);
				}
				parameters.add(new BasicNameValuePair("page", lque.getPage()
						+ ""));
				parameters.add(new BasicNameValuePair("limit",
						Constant.limitCount + ""));
			}

			@Override
			protected Map<String, Object> doInBackground() {
				return HttpDataUtil.executeHttp(getApplicationContext(),
						parameters, R.string.url_ask_list);
			}

			@Override
			protected void onPostExecute(Map<String, Object> result) {
				super.onPostExecute(result);
				HttpDataUtil.requestBackHandle(result, new BaseRequestRecall() {

					@Override
					public void resultSuccess(Object msg) {
						listcln = JSON.parseObject(msg.toString(),
								QuestionCollection.class);
						if (listcln.getYi18() != null) {
							if (flag) {
								lque.setLque(listcln.getYi18());
								CacheSetting.instance().put("Question",
										msg.toString());
							} else {
								lque.getLque().addAll(listcln.getYi18());
							}
							if (listcln.getYi18().size() < Constant.limitCount) {
								lque.setHasmore(false);
							} else {
								lque.setHasmore(true);
							}
						}
						anadapter.notifyDataSetChanged();
					}

					@Override
					public void resultFailed(Object msg) {
						lque.setPage(lque.getPage() - 1);
						showToast(msg.toString());
					}

					@Override
					public void requestFailed(Object msg) {
						lque.setPage(lque.getPage() - 1);
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
		pulllv_newsfocus.footerRefreshSetting();
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
		if (!lque.isHasmore()) {
			pulllv_newsfocus.setHasMoreData(false);
		}
	}
}
