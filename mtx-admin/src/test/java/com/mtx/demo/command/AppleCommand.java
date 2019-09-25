package com.mtx.demo.command;

/**
 * Created by Administrator on 2018/4/25.
 */
public class AppleCommand extends CommonCommand{
	public AppleCommand(Store store) {
		super(store);
	}

	@Override
	public void sell() {
		this.getStore().sellApple();

	}
}
