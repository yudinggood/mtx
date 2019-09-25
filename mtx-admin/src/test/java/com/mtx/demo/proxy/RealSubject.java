package com.mtx.demo.proxy;

/**
 * Created by Administrator on 2018/4/19.
 */
public class RealSubject implements Subject{//出版社
	@Override
	public void sellBook() {
		System.out.println("卖书");
	}
}
