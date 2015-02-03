package com.hmhelper.setting;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("UseSparseArrays")
public class Constant {
	// 新闻页面缓存
	public static Map<Integer, Fragment> newsCacheFragments = new HashMap<Integer, Fragment>();
	// 文化知识页面缓存
	public static Map<Integer, Fragment> culturalCacheFragments = new HashMap<Integer, Fragment>();

	// Imageloader相关
	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.nostra13.example.universalimageloader.FRAGMENT_INDEX";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}

	// 分页条数
	public static final int limitCount = 20;

	// 没有图片
	public static final String Nopic = "img/top/default.jpg";
}
