package com.hmhelper.adapter;

import com.hmhelper.activity.R;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.HealthMessage;
import com.hmhelper.entity.ListHealthMessage;
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

public class HealthMessageAdapter extends BaseAdapter implements
		OnScrollListener {

	private Context context;
	private LayoutInflater inflater;
	private ListHealthMessage lHmsg;
	private HealthMsgHolder holder;
	private HealthMessage hmsg;
	private boolean isScrolling;

	public HealthMessageAdapter(Context context, ListHealthMessage lHmsg) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.lHmsg = lHmsg;
	}

	@Override
	public int getCount() {
		if (lHmsg == null) {
			return 0;
		} else {
			if (lHmsg.getLhmsg() == null) {
				return 0;
			} else {
				return lHmsg.getLhmsg().size();
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
			holder = new HealthMsgHolder();
			convertView = inflater.inflate(R.layout.healthmessage_item, parent,
					false);
			holder.imgnews = (ImageView) convertView
					.findViewById(R.id.hmsg_item_img);
			holder.lbg = (LinearLayout) convertView
					.findViewById(R.id.hmsg_item_ltbg);
			holder.tvtitle = (TextView) convertView
					.findViewById(R.id.hmsg_item_title);
			holder.tvtag = (TextView) convertView
					.findViewById(R.id.hmsg_item_tag);
			holder.tvrcount = (TextView) convertView
					.findViewById(R.id.hmsg_item_rcount);
			holder.tvtime = (TextView) convertView
					.findViewById(R.id.hmsg_item_time);
			convertView.setTag(holder);
		} else {
			holder = (HealthMsgHolder) convertView.getTag();
		}
		holder.imgnews.setVisibility(View.VISIBLE);
		holder.lbg.setBackgroundColor(context.getResources().getColor(
				R.color.focs_img_cover));
		holder.tvtitle.setTextColor(context.getResources().getColor(
				R.color.white));
		hmsg = lHmsg.getLhmsg().get(position);
		if (hmsg.getImg() == null) {
			holder.imgnews.setVisibility(View.GONE);
			holder.lbg.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			holder.tvtitle.setTextColor(context.getResources().getColor(
					R.color.black));
		} else {
			if (!isScrolling) {
				ImageLoader.getInstance().displayImage(
						StringUtils.getImgUrl(context, hmsg.getImg()),
						holder.imgnews,
						HMaplication.instance.getImageOptions(null));
			}
		}
		holder.tvtitle.setText(hmsg.getTitle());
		holder.tvtag.setText(hmsg.getTag());
		holder.tvrcount.setText(hmsg.getCount() + "");
		try {
			holder.tvtime.setText(HMaplication.instance.NewFormat
					.format(HMaplication.instance.OldFormat.parse(hmsg
							.getTime())));
		} catch (Exception e) {
			holder.tvtime.setText(hmsg.getTime());
			LogUtils.d("转换日期错误", e);
		}
		return convertView;
	}

	class HealthMsgHolder {
		private ImageView imgnews;
		private LinearLayout lbg;
		private TextView tvtitle;
		private TextView tvtag;
		private TextView tvrcount;
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
