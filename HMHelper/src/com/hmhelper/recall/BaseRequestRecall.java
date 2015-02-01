package com.hmhelper.recall;

public abstract class BaseRequestRecall {
	public abstract void requestFailed(Object msg);

	public abstract void resultSuccess(Object msg);

	public abstract void resultFailed(Object msg);
}
