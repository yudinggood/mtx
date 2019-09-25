package com.mtx.demo.state;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Person {
	private int hour;
	private State state;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	public void dosomething(){
		state.dosomething(this);
		state=new MorningState();
	}

	public Person() {
		state=new MorningState();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
