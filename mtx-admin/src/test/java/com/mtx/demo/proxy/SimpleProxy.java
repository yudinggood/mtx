package com.mtx.demo.proxy;

/**
 * Created by Administrator on 2018/4/19.
 */
public class SimpleProxy implements Subject{//静态代理    就是在 之前与之后加入了方法        书店是一个代理，帮出版社卖书的前后加入某些动作
	private RealSubject realSubject;



	@Override
	public void sellBook() {
		daZhe();
		if(realSubject==null){
			realSubject=new RealSubject();
		}
		realSubject.sellBook();
		yuSon();
	}

	public void daZhe() {
		System.out.println("打折");
	}
	public void yuSon() {
		System.out.println("运送");
	}
}
