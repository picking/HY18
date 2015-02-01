package com.hmhelper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.hmhelper.entity.Base;
import com.hmhelper.recall.BaseRequestRecall;
import com.hmhelper.setting.HttpSetting;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

public class HttpDataUtil {
	public static void getHttpData(Context context, int urlid,
			final BaseRequestRecall interf) {
		getHttpData(context, null, urlid, null, interf);
	}

	public static void getHttpData(Context context, RequestParams params,
			int urlid, final BaseRequestRecall interf) {
		getHttpData(context, params, urlid, null, interf);
	}

	public static void getHttpData(Context context, RequestParams params,
			int urlid, HttpHandler<String> httpHandler,
			final BaseRequestRecall interf) {
		final Map<String, Object> daMap = new HashMap<String, Object>();
		final String url = StringUtils.getUrl(context, urlid);
		HttpSetting.instance(context);
		HttpSetting.http.configCurrentHttpCacheExpiry(1000 * 10);
		httpHandler = HttpSetting.http.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException exception, String msg) {
						LogUtils.d("弹出异常", exception);
						LogUtils.d("异常信息：" + msg);
						daMap.put("RQS", false);
						daMap.put("S", false);
						daMap.put("R", "请求失败：" + msg);
						requestBackHandle(daMap, interf);
					}

					@Override
					public void onSuccess(ResponseInfo<String> Response) {
						LogUtils.d("返回信息：" + Response.result);
						daMap.put("RQS", true);
						Base rq = JSON.parseObject(Response.result, Base.class);
						daMap.put("S", rq.isSuccess());
						if (rq.isSuccess()) {
							daMap.put("R", Response.result);
						} else {
							daMap.put("R", " ");
						}
						requestBackHandle(daMap, interf);
					}

					@Override
					public void onCancelled() {
						super.onCancelled();
						daMap.put("RQS", false);
						daMap.put("S", false);
						daMap.put("R", "请求取消");
						requestBackHandle(daMap, interf);
					}

					@Override
					public void onStart() {
						super.onStart();
						LogUtils.d("访问url:" + url);
					}

				});
	}

	public static void requestBackHandle(Map<String, Object> dMap,
			BaseRequestRecall interf) {
		if ((boolean) dMap.get("RQS")) {
			if ((boolean) dMap.get("S")) {
				interf.resultSuccess(dMap.get("R"));
			} else {
				interf.resultFailed(dMap.get("R"));
			}
		} else {
			interf.requestFailed(dMap.get("R"));
		}
	}

	public static Map<String, Object> executeHttp(Context context,
			List<NameValuePair> postParameters, int urlid) {
		Map<String, Object> daMap = new HashMap<String, Object>();
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			String url = StringUtils.getUrl(context, urlid);
			LogUtils.d("请求Url地址:" + url);
			request.setURI(new URI(url));
			request.setHeader("Charset", "UTF-8");
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, "UTF-8");
			request.setEntity(formEntity);

			HttpSetting.instance(context);
			HttpResponse response = HttpSetting.http.getHttpClient().execute(
					request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			LogUtils.d("数据：" + strBuffer.toString());
			daMap.put("RQS", true);
			Base rq = JSON.parseObject(strBuffer.toString(), Base.class);
			daMap.put("S", rq.isSuccess());
			if (rq.isSuccess()) {
				daMap.put("R", strBuffer.toString());
			} else {
				daMap.put("R", " ");
			}
		} catch (Exception e) {
			LogUtils.d("http请求异常", e);
			daMap.put("RQS", false);
			daMap.put("S", false);
			daMap.put("R", "请求失败");
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					LogUtils.d("http请求io关闭异常", e);
				}
			}
		}
		return daMap;
	}

	public static Map<String, Object> executeHttp(Context context, int urlid) {
		Map<String, Object> daMap = new HashMap<String, Object>();
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			String url = StringUtils.getUrl(context, urlid);
			LogUtils.d("请求Url地址:" + url);
			request.setURI(new URI(url));
			request.setHeader("Charset", "UTF-8");

			HttpSetting.instance(context);
			HttpResponse response = HttpSetting.http.getHttpClient().execute(
					request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			LogUtils.d("数据：" + strBuffer.toString());
			daMap.put("RQS", true);
			Base rq = JSON.parseObject(strBuffer.toString(), Base.class);
			daMap.put("S", rq.isSuccess());
			if (rq.isSuccess()) {
				daMap.put("R", strBuffer.toString());
			} else {
				daMap.put("R", " ");
			}
		} catch (Exception e) {
			LogUtils.d("http请求异常", e);
			daMap.put("RQS", false);
			daMap.put("S", false);
			daMap.put("R", "请求失败");
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					LogUtils.d("http请求io关闭异常", e);
				}
			}
		}
		return daMap;
	}
}
