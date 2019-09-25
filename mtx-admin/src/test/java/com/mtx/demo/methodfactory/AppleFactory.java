package com.mtx.demo.methodfactory;

/**
 * Created by Administrator on 2018/4/20.
 */
public class AppleFactory implements FruitFactory{
	@Override
	public Fruit getFruit() {
		return new Apple();
	}
}
