package com.mtx.demo.singleton;

/**
 * Created by Administrator on 2018/4/19.
 */
public class Singleton {//双重检查 的 单例  SimpleSingleton
	private volatile static Singleton singleton;
	private String name;
	private Singleton(){};
	public static Singleton getInstance(){
		if(singleton==null){
			synchronized (Singleton.class){//Singleton没有实例，此锁无用  类锁
				if(singleton==null){
					singleton=new Singleton();
				}
			}
		}
		return singleton;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
