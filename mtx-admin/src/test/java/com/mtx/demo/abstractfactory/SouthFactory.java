package com.mtx.demo.abstractfactory;

/**
 * Created by Administrator on 2018/4/19.
 */
public class SouthFactory implements AbstractFactory{
	@Override
	public Fruit getApple() {
		return new SouthApple();
	}

	@Override
	public Fruit getBanana() {
		return new SouthBanana();
	}
}
