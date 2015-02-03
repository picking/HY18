package com.hmhelper.adapter;

import com.hmhelper.activity.R;
import com.hmhelper.application.HMaplication;
import com.hmhelper.entity.CulturalKnowdge;
import com.hmhelper.entity.ListCulturalKnowledge;
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

public class HealthKnowledgeAdapter extends BaseAdapter implements
		OnScrollListener {

	private Context context;
	private LayoutInflater inflater;
	private ListCulturalKnowledge lckg;
	private HealthKngHolder holder;
	private CulturalKnowdge ckg;
	private boolean isScrolling;

	public HealthKnowledgeAdapter(Context context, ListCulturalKnowledge lckg) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.lckg = lckg;
	}

	@Override
	public int getCount() {
		if (lckg == null) {
			return 0;
		} else {
			if (lckg.getLckg() == null) {
				return 0;
			} else {
				return lckg.getLckg().size();
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
			holder = new HealthKngHolder();
			convertView = inflater.inflate(R.layout.healthknowledge_item,
					parent, false);
			holder.imgnews = (ImageView) convertView
					.findViewById(R.id.ckg_item_img);
			holder.lbg = (LinearLayout) convertView
					.findViewById(R.id.ckg_item_ltbg);
			holder.tvtitle = (TextView) convertView
					.findViewById(R.id.ckg_item_title);
			holder.tvtag = (TextView) convertView
					.findViewById(R.id.ckg_item_tag);
			holder.tvrcount = (TextView) convertView
					.findViewById(R.id.ckg_item_rcount);
			holder.tvtime = (TextView) convertView
					.findViewById(R.id.ckg_item_time);
			convertView.setTag(holder);
		} else {
			holder = (HealthKngHolder) convertView.getTag();
		}
		holder.imgnews.setVisibility(View.VISIBLE);
		holder.lbg.setBackgroundColor(context.getResources().getColor(
				R.color.focs_img_cover));
		holder.tvtitle.setTextColor(context.getResources().getColor(
				R.color.white));
		ckg = lckg.getLckg().get(position);
		if (ckg.getImg() == null) {
			holder.imgnews.setVisibility(View.GONE);
			holder.lbg.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			holder.tvtitle.setTextColor(context.getResources().getColor(
					R.color.black));
		} else {
			if (!isScrolling) {
				ImageLoader.getInstance().displayImage(
						StringUtils.getImgUrl(context, ckg.getImg()),
						holder.imgnews,
						HMaplication.instance.getImageOptions(null));
			}
		}
		holder.tvtitle.setText(ckg.getTitle());
		holder.tvtag.setText(ckg.getClassName());
		holder.tvrcount.setText(ckg.getCount() + "");
		try {
			holder.tvtime
					.setText(HMaplication.instance.NewFormat
							.format(HMaplication.instance.OldFormat.parse(ckg
									.getTime())));
		} catch (Exception e) {
			holder.tvtime.setText(ckg.getTime());
			LogUtils.d("转换日期错误", e);
		}
		return convertView;
	}

	class HealthKngHolder {
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
