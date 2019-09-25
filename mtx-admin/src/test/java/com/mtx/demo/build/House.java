package com.mtx.demo.build;

/**
 * Created by Administrator on 2018/4/20.
 */
public class House {
	private String floor;
	private String wall;
	private String top;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	@Override
	public String toString() {
		return "House{" +
				"floor='" + floor + '\'' +
				", wall='" + wall + '\'' +
				", top='" + top + '\'' +
				'}';
	}
}
