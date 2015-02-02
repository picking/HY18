package com.hmhelper.entity;

import java.util.List;

public class ListHealthMessage extends BasePage {

	private static final long serialVersionUID = 1L;

	private List<HealthMessage> lhmsg;

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

	public List<HealthMessage> getLhmsg() {
		return lhmsg;
	}

	public void setLhmsg(List<HealthMessage> lhmsg) {
		this.lhmsg = lhmsg;
	}
}
