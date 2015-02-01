package com.hmhelper.entity;

import java.util.List;

public class ListNewsFocus extends BasePage {

	private static final long serialVersionUID = 1L;

	private List<NewsFocus> lnews;

	public List<NewsFocus> getLnews() {
		return lnews;
	}

	public void setLnews(List<NewsFocus> lnews) {
		this.lnews = lnews;
	}

	@Override
	public boolean isHasmore() {
		// TODO Auto-generated method stub
		return super.isHasmore();
	}

	@Override
	public void setHasmore(boolean hasmore) {
		// TODO Auto-generated method stub
		super.setHasmore(hasmore);
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return super.getPage();
	}

	@Override
	public void setPage(int page) {
		// TODO Auto-generated method stub
		super.setPage(page);
	}

}
