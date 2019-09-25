package com.mtx.demo.methodfactory;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface FruitFactory {//工厂方法模式   用于创建其他类的实例    本类用来创建新的其他种类的工厂
	public Fruit getFruit();
}
