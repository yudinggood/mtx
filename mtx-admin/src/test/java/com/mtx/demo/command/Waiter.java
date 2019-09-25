package com.mtx.demo.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Waiter {
	private List<CommonCommand> order =new ArrayList<CommonCommand>();


	public void setOrder(CommonCommand commonCommand) {
		order.add(commonCommand);
	}
	public void remove(CommonCommand commonCommand) {
		order.remove(commonCommand);
	}

	public  void sell(){
		for (CommonCommand commonCommand:order){
			commonCommand.sell();
		}
	}
}
