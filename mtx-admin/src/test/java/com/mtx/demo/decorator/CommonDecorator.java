package com.mtx.demo.decorator;

/**
 * Created by Administrator on 2018/4/20.
 */
public abstract class CommonDecorator implements Car{//装饰模式 用于给对象添加功能
	private Car car;

	public CommonDecorator(Car car) {
		this.car = car;
	}
	public abstract void show();

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
