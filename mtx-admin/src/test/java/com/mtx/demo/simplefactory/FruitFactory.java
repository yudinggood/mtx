package com.mtx.demo.simplefactory;

/**
 * Created by Administrator on 2018/4/19.
 */
public class FruitFactory {//简单工厂   用于创建其他类的实例
	public static Fruit getFruit(String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class fruit =Class.forName(type);
		return (Fruit)fruit.newInstance();
	}
}
