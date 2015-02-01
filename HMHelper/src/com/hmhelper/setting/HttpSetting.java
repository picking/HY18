package com.hmhelper.setting;

import android.content.Context;
import com.lidroid.xutils.HttpUtils;

public class HttpSetting {
	public static HttpSetting set = null;
	public static HttpUtils http = null;

	public static HttpSetting instance(Context context) {
		if (set == null) {
			set = new HttpSetting();
			http = new HttpUtils();
			http.configTimeout(30000);
			http.configDefaultHttpCacheExpiry(1000 * 10);
			http.configHttpCacheSize(0);
			http.configRequestThreadPoolSize(5);
		}
		return set;
	}

	public static void setNull() {
		set = null;
	}
}
