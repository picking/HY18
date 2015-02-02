package com.hmhelper.activity;

import com.alibaba.fastjson.JSON;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.NewsFocusDetialCollction;
import com.hmhelper.recall.BaseRequestRecall;
import com.hmhelper.utils.AnimationUtil;
import com.hmhelper.utils.HttpDataUtil;
import com.hmhelper.utils.StringUtils;
import com.hmhelper.view.FrameWebView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class NewsFocusDetialActivity extends BaseActivity {

	@ViewInject(R.id.nsf_srv_contetnt)
	private ScrollView nsf_srv_contetnt;
	@ViewInject(R.id.nsf_tv_tittle)
	private TextView nsf_tv_tittle;
	@ViewInject(R.id.nsf_tv_from)
	private TextView nsf_tv_from;
	@ViewInject(R.id.nsf_tv_time)
	private TextView nsf_tv_time;
	@ViewInject(R.id.nsf_img_news)
	private ImageView nsf_img_news;
	@ViewInject(R.id.nsf_ll_loading)
	private LinearLayout nsf_ll_loading;
	@ViewInject(R.id.nsf_pbar_loading)
	private ProgressBarCircularIndeterminate nsf_pbar_loading;
	@ViewInject(R.id.nsf_ll_refresh)
	private LinearLayout nsf_ll_refresh;
	@ViewInject(R.id.nsf_img_refresh)
	private ImageView nsf_img_refresh;
	@ViewInject(R.id.nsf_tv_refreshmsg)
	private TextView nsf_tv_refreshmsg;
	@ViewInject(R.id.nsf_web_detial)
	private FrameWebView nsf_web_detial;

	private HttpHandler<String> httpRqt;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_newsfocus_detial);
		HMaplication.instance.addActivity(this);
		ViewUtils.inject(this);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		nsf_srv_contetnt.postDelayed(new Runnable() {

			@Override
			public void run() {
				initData();
			}
		}, 1000);
	}

	private void initData() {
		int id = getIntent().getExtras().getInt("NEWSFOCUSID", -1);
		if (-1 != id) {
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("id", id + "");
			HttpDataUtil.getHttpData(getApplicationContext(), params,
					R.string.url_newsfocus_detial, httpRqt,
					new BaseRequestRecall() {

						@Override
						public void resultSuccess(Object msg) {
							NewsFocusDetialCollction nsfc = JSON.parseObject(
									msg.toString(),
									NewsFocusDetialCollction.class);
							setData(nsfc);
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
		nsf_img_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nsf_pbar_loading.setVisibility(View.VISIBLE);
				nsf_ll_refresh.setVisibility(View.GONE);
				initData();
			}
		});
	}

	private void setData(NewsFocusDetialCollction nsfc) {
		nsf_ll_loading.setVisibility(View.GONE);
		nsf_srv_contetnt.setVisibility(View.VISIBLE);
		nsf_tv_tittle.setText(nsfc.getYi18().getTitle());
		nsf_tv_from.setText(nsfc.getYi18().getFrom());
		try {
			nsf_tv_time.setText(HMaplication.instance.NewFormat
					.format(HMaplication.instance.OldFormat.parse(nsfc
							.getYi18().getTime())));
		} catch (Exception e) {
			nsf_tv_time.setText(nsfc.getYi18().getTime());
			LogUtils.d("转换日期错误", e);
		}
		if (nsfc.getYi18().getImg() == null) {
			nsf_img_news.setVisibility(View.GONE);
		} else {
			ImageLoader.getInstance().displayImage(
					StringUtils.getImgUrl(getApplicationContext(), nsfc
							.getYi18().getImg()), nsf_img_news,
					HMaplication.instance.getImageOptions(null));
		}
		setWebView(nsfc.getYi18().getMessage());
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView(String content) {
		nsf_web_detial.getSettings().setDefaultTextEncodingName("utf-8");
		// nsf_web_detial.getSettings().setUseWideViewPort(true); //自适应屏幕
		// nsf_web_detial.getSettings().setLoadWithOverviewMode(true);//
		// 缩放至屏幕的大小
		// nsf_web_detial.setInitialScale(100); // 初始化时缩放
		nsf_web_detial.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.SINGLE_COLUMN); // 网页的内容成单列显示
		nsf_web_detial.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不使用缓存
		nsf_web_detial.getSettings().setJavaScriptEnabled(true); // 支持JavaScript
		nsf_web_detial.setHorizontalScrollBarEnabled(false); // 横向滚动条
		nsf_web_detial.getSettings().setSupportZoom(false); // 支持缩放
		nsf_web_detial.getSettings().setBuiltInZoomControls(false);// 显示放大缩小
		// nsf_web_detial.loadDataWithBaseURL(null,
		// Html.fromHtml(content).toString(), "text/html", "utf-8", null);
		nsf_web_detial.loadDataWithBaseURL(null, content, "text/html", "utf-8",
				null);
	}

	private void loadDataError(String msg) {
		nsf_pbar_loading.setVisibility(View.GONE);
		nsf_ll_refresh.setVisibility(View.VISIBLE);
		if (StringUtils.isNotEmpty(msg)) {
			nsf_tv_refreshmsg.setText(msg);
		}
	}

	private void back() {
		HMaplication.instance.toFinish(getClass());
		AnimationUtil.backActivity(NewsFocusDetialActivity.this);
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
