package com.mtx.demo.build;

/**
 * Created by Administrator on 2018/4/20.
 */
public class HighBulider implements CommonBuilder{
	House house=new House();
	@Override
	public void makeFloor() {
		house.setFloor("高级地板");
	}

	@Override
	public void makeWall() {
		house.setWall("高级墙");
	}

	@Override
	public void makeTop() {
		house.setTop("高级屋顶");
	}
	@Override
	public House getHouse(){
		return house;
	}
}
