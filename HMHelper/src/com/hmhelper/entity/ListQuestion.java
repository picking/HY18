package com.hmhelper.entity;

import java.util.List;

public class ListQuestion extends BasePage {

	private static final long serialVersionUID = 1L;

	private List<Question> lque;

	public List<Question> getLque() {
		return lque;
	}

	public void setLque(List<Question> lque) {
		this.lque = lque;
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
