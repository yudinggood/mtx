package com.mtx.demo.build;

/**
 * Created by Administrator on 2018/4/20.
 */
public class HouseDirector {//设计者

	public void makeHouse(CommonBuilder commonBuilder){
		commonBuilder.makeFloor();
		commonBuilder.makeWall();
		commonBuilder.makeTop();
	}
}
