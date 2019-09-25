package com.mtx.demo.chain;

/**
 * Created by Administrator on 2018/4/20.
 */
public class CarHeadHandler extends CarHandler{
	@Override
	public void handlerCar() {
		System.out.println("组装车头");
		if(this.carHandler!=null){
			this.carHandler.handlerCar();
		}
	}
}
