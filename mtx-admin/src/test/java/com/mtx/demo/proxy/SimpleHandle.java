package com.mtx.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/19.
 */
public class SimpleHandle implements InvocationHandler{//动态代理  使用jdk自带方法
	private RealSubject realSubject;

	public void setRealSubject(RealSubject realSubject) {
		this.realSubject = realSubject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		daZhe();
		Object result=method.invoke(realSubject,args);
		yuSon();
		return result;
	}

	public void daZhe() {
		System.out.println("打折");
	}
	public void yuSon() {
		System.out.println("运送");
	}
}
