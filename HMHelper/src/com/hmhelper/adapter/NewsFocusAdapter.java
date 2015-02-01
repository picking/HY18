package com.hmhelper.adapter;

import com.hmhelper.activity.R;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.ListNewsFocus;
import com.hmhelper.entity.NewsFocus;
import com.hmhelper.setting.Constant;
import com.hmhelper.utils.StringUtils;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsFocusAdapter extends BaseAdapter implements OnScrollListener {

	private Context context;
	private LayoutInflater inflater;
	private ListNewsFocus lFocus;
	private NewsFocusHolder holder;
	private NewsFocus newsf;
	private boolean isScrolling;

	public NewsFocusAdapter(Context context, ListNewsFocus lFocus) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.lFocus = lFocus;
	}

	@Override
	public int getCount() {
		if (lFocus == null) {
			return 0;
		} else {
			if (lFocus.getLnews() == null) {
				return 0;
			} else {
				return lFocus.getLnews().size();
			}
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new NewsFocusHolder();
			convertView = inflater.inflate(R.layout.newsfocus_item, parent,
					false);
			holder.imgnews = (ImageView) convertView
					.findViewById(R.id.focs_item_img);
			holder.lbg = (LinearLayout) convertView
					.findViewById(R.id.focs_item_ltbg);
			holder.tvtitle = (TextView) convertView
					.findViewById(R.id.focs_item_title);
			holder.tvkeywords = (TextView) convertView
					.findViewById(R.id.focs_item_keywords);
			holder.tvfrom = (TextView) convertView
					.findViewById(R.id.focs_item_from);
			holder.tvtime = (TextView) convertView
					.findViewById(R.id.focs_item_time);
			convertView.setTag(holder);
		} else {
			holder = (NewsFocusHolder) convertView.getTag();
		}
		holder.imgnews.setVisibility(View.VISIBLE);
		holder.lbg.setBackgroundColor(context.getResources().getColor(
				R.color.focs_img_cover));
		holder.tvtitle.setTextColor(context.getResources().getColor(
				R.color.white));
		newsf = lFocus.getLnews().get(position);
		if (Constant.Nopic.equals(newsf.getImg())) {
			holder.imgnews.setVisibility(View.GONE);
			holder.lbg.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			holder.tvtitle.setTextColor(context.getResources().getColor(
					R.color.black));
		} else {
			if (!isScrolling) {
				ImageLoader.getInstance().displayImage(
						StringUtils.getImgUrl(context, newsf.getImg()),
						holder.imgnews,
						HMaplication.instance.getImageOptions(null));
			}
		}
		holder.tvtitle.setText(newsf.getTitle());
		holder.tvkeywords.setText(newsf.getKeywords());
		holder.tvfrom.setText(newsf.getFrom());
		try {
			holder.tvtime.setText(HMaplication.instance.NewFormat
					.format(HMaplication.instance.OldFormat.parse(newsf
							.getTime())));
		} catch (Exception e) {
			holder.tvtime.setText(newsf.getTime());
			LogUtils.d("转换日期错误", e);
		}
		return convertView;
	}

	class NewsFocusHolder {
		private ImageView imgnews;
		private LinearLayout lbg;
		private TextView tvtitle;
		private TextView tvkeywords;
		private TextView tvfrom;
		private TextView tvtime;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) { // 不滚动状态
			isScrolling = false;
			this.notifyDataSetChanged();
		} else {
			isScrolling = true;
		}

	}

}
