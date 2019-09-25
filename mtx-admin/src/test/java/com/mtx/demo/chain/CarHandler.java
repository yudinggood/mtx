package com.mtx.demo.chain;

/**
 * Created by Administrator on 2018/4/20.
 */
public abstract class CarHandler {//责任链模式
	protected CarHandler carHandler;
	public abstract void handlerCar();
	public CarHandler setNextHandler(CarHandler carHandler){
		this.carHandler=carHandler;
		return this.carHandler;
	}
}
