package com.mtx.demo.facade;

/**
 * Created by Administrator on 2018/4/25.
 */
public class CommonFacade {//外观模式  就是把内部系统隔绝，只通过这个外观类访问
	private SystemA systemA;
	private SystemB systemB;

	public CommonFacade() {
		systemA=new SystemA();
		systemB=new SystemB();
	}
	public void doSomething(){
		this.systemA.doSomething();
		this.systemB.doSomething();
	}
	public void doSomethingA(){
		this.systemA.doSomething();
	}
}
