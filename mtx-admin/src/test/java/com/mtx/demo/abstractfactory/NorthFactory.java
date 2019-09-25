package com.mtx.demo.abstractfactory;

/**
 * Created by Administrator on 2018/4/19.
 */
public class NorthFactory implements AbstractFactory{
	@Override
	public Fruit getApple() {
		return new NorthApple();
	}

	@Override
	public Fruit getBanana() {
		return new NorthBanana();
	}
}
