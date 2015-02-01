package com.hmhelper.entity;

import java.io.Serializable;

public class BaseSingleEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int total;
	private T yi18;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public T getYi18() {
		return yi18;
	}

	public void setYi18(T yi18) {
		this.yi18 = yi18;
	}

}
