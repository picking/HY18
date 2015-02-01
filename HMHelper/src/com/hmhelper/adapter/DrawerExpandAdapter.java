package com.hmhelper.adapter;

import java.util.List;

import com.hmhelper.activity.R;
import com.hmhelper.entity.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerExpandAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private LayoutInflater mInflater = null;
	private String[] mGroupStrings = null;
	private List<List<Item>> mData = null;

	public DrawerExpandAdapter(Context ctx, List<List<Item>> list) {
		mContext = ctx;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGroupStrings = mContext.getResources().getStringArray(R.array.total_groups);
		mData = list;
	}

	public void setData(List<List<Item>> list) {
		mData = list;
	}

	@Override
	public int getGroupCount() {
		return mData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mData.get(groupPosition).size();
	}

	@Override
	public List<Item> getGroup(int groupPosition) {
		return mData.get(groupPosition);
	}

	@Override
	public Item getChild(int groupPosition, int childPosition) {
		return mData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.group_item_layout, null);
		}
		GroupViewHolder holder = new GroupViewHolder();
		holder.mmGroupImg = (ImageView) convertView.findViewById(R.id.group_img);
		holder.mmGroupImg.setImageResource(R.drawable.expand_indicator_close);
		if (isExpanded) {
			holder.mmGroupImg.setImageResource(R.drawable.expand_indicator_open);
		}
		holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
		holder.mGroupName.setText(mGroupStrings[groupPosition]);
		holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
		holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]");
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.child_item_layout, null);
		}
		ChildViewHolder holder = new ChildViewHolder();
		holder.mIcon = (ImageView) convertView.findViewById(R.id.img);
		holder.mIcon.setBackgroundResource(getChild(groupPosition, childPosition).getImageId());
		holder.mChildName = (TextView) convertView.findViewById(R.id.item_name);
		holder.mChildName.setText(getChild(groupPosition, childPosition).getName());
		holder.mDetail = (TextView) convertView.findViewById(R.id.item_detail);
		holder.mDetail.setText(getChild(groupPosition, childPosition).getDetail());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// 很重要：实现ChildView点击事件，必须返回true
		return true;
	}

	private class GroupViewHolder {
		ImageView mmGroupImg;
		TextView mGroupName;
		TextView mGroupCount;
	}

	private class ChildViewHolder {
		ImageView mIcon;
		TextView mChildName;
		TextView mDetail;
	}

}
