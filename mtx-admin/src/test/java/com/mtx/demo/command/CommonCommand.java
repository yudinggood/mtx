package com.mtx.demo.command;

/**
 * Created by Administrator on 2018/4/25.
 */
public abstract class CommonCommand {//命令模式
	private Store store;

	public CommonCommand(Store store) {
		this.store = store;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public abstract void sell();
}
