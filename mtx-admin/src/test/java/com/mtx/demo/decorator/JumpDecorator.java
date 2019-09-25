package com.mtx.demo.decorator;

/**
 * Created by Administrator on 2018/4/20.
 */
public class JumpDecorator extends CommonDecorator{
	public JumpDecorator(Car car) {
		super(car);
	}

	@Override
	public void show() {
		this.getCar().show();
		this.jump();
	}

	@Override
	public void run() {

	}
	public void jump(){
		System.out.println("可以跳");
	}
}
