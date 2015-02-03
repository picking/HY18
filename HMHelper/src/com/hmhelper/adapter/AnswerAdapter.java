package com.hmhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hmhelper.activity.R;
import com.hmhelper.entity.ListQuestion;
import com.hmhelper.entity.Question;

public class AnswerAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ListQuestion lque;
	private HealthKngHolder holder;
	private Question que;

	public AnswerAdapter(Context context, ListQuestion lque) {
		inflater = LayoutInflater.from(context);
		this.lque = lque;
	}

	@Override
	public int getCount() {
		if (lque == null) {
			return 0;
		} else {
			if (lque.getLque() == null) {
				return 0;
			} else {
				return lque.getLque().size();
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
			convertView = inflater.inflate(R.layout.question_item, parent,
					false);
			holder.tvtitle = (TextView) convertView
					.findViewById(R.id.que_item_title);
			holder.tvtag = (TextView) convertView
					.findViewById(R.id.que_item_tag);
			holder.tvrcount = (TextView) convertView
					.findViewById(R.id.que_item_rcount);
			convertView.setTag(holder);
		} else {
			holder = (HealthKngHolder) convertView.getTag();
		}
		que = lque.getLque().get(position);
		holder.tvtitle.setText(que.getTitle());
		holder.tvtag.setText(que.getClassname());
		holder.tvrcount.setText(que.getCount() + "");
		return convertView;
	}

	class HealthKngHolder {
		private TextView tvtitle;
		private TextView tvtag;
		private TextView tvrcount;
	}

}
