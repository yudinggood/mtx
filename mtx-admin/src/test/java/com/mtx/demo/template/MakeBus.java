package com.mtx.demo.template;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MakeBus extends MakeCar{
	@Override
	public void makeHead() {
		System.out.println("Bus安装车头");
	}

	@Override
	public void makeBody() {
		System.out.println("Bus安装车身");
	}
}
