package com.hmhelper.activity;

import com.alibaba.fastjson.JSON;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.AnswerDetialCollection;
import com.hmhelper.recall.BaseRequestRecall;
import com.hmhelper.utils.AnimationUtil;
import com.hmhelper.utils.HttpDataUtil;
import com.hmhelper.utils.StringUtils;
import com.hmhelper.view.FrameWebView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AnswerDetialActivity extends BaseActivity {

	@ViewInject(R.id.and_srv_contetnt)
	private ScrollView and_srv_contetnt;
	@ViewInject(R.id.and_tv_tittle)
	private TextView and_tv_tittle;
	@ViewInject(R.id.and_tv_tag)
	private TextView and_tv_tag;
	@ViewInject(R.id.and_tv_rcount)
	private TextView and_tv_rcount;
	@ViewInject(R.id.and_ll_loading)
	private LinearLayout and_ll_loading;
	@ViewInject(R.id.and_pbar_loading)
	private ProgressBarCircularIndeterminate and_pbar_loading;
	@ViewInject(R.id.and_ll_refresh)
	private LinearLayout and_ll_refresh;
	@ViewInject(R.id.and_img_refresh)
	private ImageView and_img_refresh;
	@ViewInject(R.id.and_tv_refreshmsg)
	private TextView and_tv_refreshmsg;
	@ViewInject(R.id.and_web_detial)
	private FrameWebView and_web_detial;

	private HttpHandler<String> httpRqt;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_answer_detial);
		HMaplication.instance.addActivity(this);
		ViewUtils.inject(this);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		and_srv_contetnt.postDelayed(new Runnable() {

			@Override
			public void run() {
				initData();
			}
		}, 1000);
	}

	private void initData() {
		int id = getIntent().getExtras().getInt("QUESTIONID", -1);
		if (-1 != id) {
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("id", id + "");
			HttpDataUtil.getHttpData(getApplicationContext(), params,
					R.string.url_ask_detial, httpRqt, new BaseRequestRecall() {

						@Override
						public void resultSuccess(Object msg) {
							AnswerDetialCollection anc = JSON.parseObject(
									msg.toString(),
									AnswerDetialCollection.class);
							setData(anc);
						}

						@Override
						public void resultFailed(Object msg) {
							loadDataError("请求数据失败");
						}

						@Override
						public void requestFailed(Object msg) {
							loadDataError("服务器异常");
						}
					});
		} else {
			loadDataError(null);
		}
		and_img_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				and_pbar_loading.setVisibility(View.VISIBLE);
				and_ll_refresh.setVisibility(View.GONE);
				initData();
			}
		});
	}

	private void setData(AnswerDetialCollection anc) {
		and_ll_loading.setVisibility(View.GONE);
		and_srv_contetnt.setVisibility(View.VISIBLE);
		and_tv_tittle.setText(anc.getYi18().getTitle());
		and_tv_tag.setText(anc.getYi18().getClassname());
		and_tv_rcount.setText("已浏览" + anc.getYi18().getCount() + "次");
		setWebView(anc.getYi18().getAnswer().get(0).getMessage());
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView(String content) {
		and_web_detial.getSettings().setDefaultTextEncodingName("utf-8");
		// and_web_detial.getSettings().setUseWideViewPort(true); //自适应屏幕
		// and_web_detial.getSettings().setLoadWithOverviewMode(true);//
		// 缩放至屏幕的大小
		// and_web_detial.setInitialScale(100); // 初始化时缩放
		and_web_detial.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.SINGLE_COLUMN); // 网页的内容成单列显示
		and_web_detial.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不使用缓存
		and_web_detial.getSettings().setJavaScriptEnabled(true); // 支持JavaScript
		and_web_detial.setHorizontalScrollBarEnabled(false); // 横向滚动条
		and_web_detial.getSettings().setSupportZoom(false); // 支持缩放
		and_web_detial.getSettings().setBuiltInZoomControls(false);// 显示放大缩小
		// and_web_detial.loadDataWithBaseURL(null,
		// Html.fromHtml(content).toString(), "text/html", "utf-8", null);
		and_web_detial.loadDataWithBaseURL(null, content, "text/html", "utf-8",
				null);
	}

	private void loadDataError(String msg) {
		and_pbar_loading.setVisibility(View.GONE);
		and_ll_refresh.setVisibility(View.VISIBLE);
		if (StringUtils.isNotEmpty(msg)) {
			and_tv_refreshmsg.setText(msg);
		}
	}

	private void back() {
		HMaplication.instance.toFinish(getClass());
		AnimationUtil.backActivity(AnswerDetialActivity.this);
	}

	@Override
	public void onBackPressed() {
		back();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			back();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
