package com.mtx.demo.observer;

import java.util.Observable;

/**
 * Created by Administrator on 2018/4/20.
 */
public class Person extends Observable{//被观察者
	private String name;
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

		this.setChanged();
		this.notifyObservers(name);
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
