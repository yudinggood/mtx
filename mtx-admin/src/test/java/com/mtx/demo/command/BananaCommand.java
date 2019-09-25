package com.mtx.demo.command;

/**
 * Created by Administrator on 2018/4/25.
 */
public class BananaCommand extends CommonCommand{
	public BananaCommand(Store store) {
		super(store);
	}

	@Override
	public void sell() {
		this.getStore().sellBanana();
	}
}
