package com.hmhelper.entity;

import java.io.Serializable;
import java.util.List;

public class BaseListEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;
	private int total;
	private List<T> yi18;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getYi18() {
		return yi18;
	}

	public void setYi18(List<T> yi18) {
		this.yi18 = yi18;
	}

}
