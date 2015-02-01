package com.hmhelper.setting;

import java.io.File;

import com.hmhelper.application.HMaplication;
import com.hmhelper.utils.ACache;

public class CacheSetting {
	public static ACache cache;

	public static ACache instance() {
		if (cache == null) {
			File file = new File(
					HMaplication.instance.DATA_CACHE_PATH);
			cache = ACache.get(file, 1000 * 1000 * 400, Integer.MAX_VALUE);// 400M
																			// 不限制条数
		}
		return cache;
	}
}
