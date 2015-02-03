package com.hmhelper.entity;

import java.io.Serializable;

public class AnswerSome implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private int topcount;
	private int id;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTopcount() {
		return topcount;
	}

	public void setTopcount(int topcount) {
		this.topcount = topcount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
