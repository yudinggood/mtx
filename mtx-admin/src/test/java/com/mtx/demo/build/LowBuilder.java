package com.mtx.demo.build;

/**
 * Created by Administrator on 2018/4/20.
 */
public class LowBuilder implements CommonBuilder{//施工队
	House house=new House();
	@Override
	public void makeFloor() {
		house.setFloor("一般地板");
	}

	@Override
	public void makeWall() {
		house.setWall("一般墙");
	}

	@Override
	public void makeTop() {
		house.setTop("一般屋顶");
	}
	@Override
	public House getHouse(){
		return house;
	}
}
