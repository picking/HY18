package com.hmhelper.application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hmhelper.activity.R;
import com.hmhelper.setting.Constant;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;

public class HMaplication extends Application {

	public static HMaplication instance;
	private static DisplayImageOptions options;
	/** 程序活动列表 */
	private List<Activity> activityList = new ArrayList<Activity>();

	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Constant.Config.DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}
		super.onCreate();
		instance = this;

		// 得到屏幕的宽度和高度
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		screenH = dm.heightPixels;

		// 不打印log
		// LogUtils.allowLog = false;

		CachePath = getApplicationContext().getExternalCacheDir().getPath();
		FilePath = getApplicationContext().getExternalFilesDir("/fs/")
				.getPath();

		// 图片下载处理
		initImageLoader(getApplicationContext());
	}

	// 当前屏幕的高宽
	public static int screenW = 0;
	public static int screenH = 0;
	public static int titleH = 0;
	public SimpleDateFormat OldFormat = new SimpleDateFormat(
			"MMM dd,yyyy KK:mm:ss aa", Locale.ENGLISH);
	public SimpleDateFormat NewFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm", Locale.getDefault());
	private static String CachePath = "/storage/sdcard0/Android/data/com.hmhelper.activity/cache";
	private static String FilePath = "/storage/sdcard0/Android/data/com.hmhelper.activity/files/fs";
	public String EXCEPTION_PATH = CachePath + "/exception/";
	public String DB_PATH = CachePath + "/db/";
	public String APK_PATH = CachePath + "/apk/";
	public String DATA_CACHE_PATH = CachePath + "/datacache";
	public String IMG_CACHE_PATH = CachePath + "/imgcache";
	public String IMG_RCACHE_PATH = FilePath + "/imgcache";
	public String VALIDATE_IMG_PATH = CachePath + "/validateimg/";

	public static String HostName = "http://api.yi18.net";
	public static String ImgName = "http://www.yi18.net";

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	public void finishAll() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}

	@SuppressWarnings("rawtypes")
	public void finishOthers(Class clazz) {
		for (int i = 0; i < activityList.size(); i++) {
			if (!clazz.getName().equals(
					activityList.get(i).getClass().getName())) {
				activityList.get(i).finish();
				activityList.remove(i);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void toFinish(Class clazz) {
		for (int i = 0; i < activityList.size(); i++) {
			if (clazz.getName()
					.equals(activityList.get(i).getClass().getName())) {
				activityList.get(i).finish();
				activityList.remove(i);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean hasClazz(Class clazz) {
		boolean flag = false;
		for (int i = 0; i < activityList.size(); i++) {
			if (clazz.getName()
					.equals(activityList.get(i).getClass().getName())) {
				flag = true;
			}
		}
		return flag;
	}

	public DisplayImageOptions getImageOptions(BitmapDisplayer displayer) {
		if (options == null) {
			if (displayer == null) {
				options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.pic_loading)
						.showImageForEmptyUri(R.drawable.pic_loaderror)
						.showImageOnFail(R.drawable.pic_loaderror)
						.cacheInMemory(true).cacheOnDisk(true)
						.considerExifParams(true).build();
			} else {
				options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.pic_loading)
						.showImageForEmptyUri(R.drawable.pic_loaderror)
						.showImageOnFail(R.drawable.pic_loaderror)
						.cacheInMemory(true).cacheOnDisk(true)
						.considerExifParams(true).displayer(displayer).build();
			}
		}
		return options;
	}

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCache(
						new UnlimitedDiscCache(new File(IMG_CACHE_PATH),
								new File(IMG_RCACHE_PATH),
								new Md5FileNameGenerator()))
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}

}
