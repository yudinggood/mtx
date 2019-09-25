package com.mtx.demo.template;

/**
 * Created by Administrator on 2018/4/25.
 */
public abstract class MakeCar {//模板方法   这个抽象类是个模板
	public abstract void makeHead();
	public abstract void makeBody();
	public void make(){
		this.makeHead();
		this.makeBody();
	}
}
