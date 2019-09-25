package com.mtx.demo.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2018/4/20.
 */
public class CommonObserver implements Observer{//观察者


	@Override
	public void update(Observable o, Object arg) {
		String name=(String)arg;
		System.out.println("对象发生变化---"+name);
	}
}
