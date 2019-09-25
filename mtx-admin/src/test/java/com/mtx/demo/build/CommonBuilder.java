package com.mtx.demo.build;

/**
 * Created by Administrator on 2018/4/20.
 */
public interface CommonBuilder {//建造者模式       开发商
	public void makeFloor();
	public void makeWall();
	public void makeTop();
	public House getHouse();
}
