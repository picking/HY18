package com.hmhelper.entity;

import java.io.Serializable;
import java.util.List;

public class AnswerDetial implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private int askclass;
	private String classname;
	private int count;
	private int scount;
	private List<AnswerSome> answer;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAskclass() {
		return askclass;
	}

	public void setAskclass(int askclass) {
		this.askclass = askclass;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}

	public List<AnswerSome> getAnswer() {
		return answer;
	}

	public void setAnswer(List<AnswerSome> answer) {
		this.answer = answer;
	}

}
