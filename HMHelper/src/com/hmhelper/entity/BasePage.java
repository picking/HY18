package com.hmhelper.entity;

import java.io.Serializable;

public class BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean hasmore = true;
	private int page = 1;

	public boolean isHasmore() {
		return hasmore;
	}

	public void setHasmore(boolean hasmore) {
		this.hasmore = hasmore;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
