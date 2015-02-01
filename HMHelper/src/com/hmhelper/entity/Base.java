package com.hmhelper.entity;

import java.io.Serializable;

public class Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;
	private int total;

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

}
