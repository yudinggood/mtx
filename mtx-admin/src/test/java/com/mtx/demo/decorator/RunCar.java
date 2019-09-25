package com.mtx.demo.decorator;

/**
 * Created by Administrator on 2018/4/20.
 */
public class RunCar implements Car{
	@Override
	public void run(){
		System.out.println("可以跑");
	}

	@Override
	public void show() {
		this.run();
	}
}
