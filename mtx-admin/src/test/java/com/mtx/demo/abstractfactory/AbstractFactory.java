package com.mtx.demo.abstractfactory;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface AbstractFactory {//抽象工厂  可以看做 工厂的工厂     本类用来创建新的同样的工厂copy
	public Fruit getApple();
	public Fruit getBanana();
}
