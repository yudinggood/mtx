package com.mtx.demo.decorator;

/**
 * Created by Administrator on 2018/4/20.
 */
public class FlyDecorator extends CommonDecorator{

	public FlyDecorator(Car car) {
		super(car);
	}

	@Override
	public void show() {
		this.getCar().show();
		this.fly();
	}
	public void fly(){
		System.out.println("可以飞");
	}

	@Override
	public void run() {

	}
}
