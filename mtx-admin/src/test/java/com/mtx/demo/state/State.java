package com.mtx.demo.state;

/**
 * Created by Administrator on 2018/4/25.
 */
public abstract class State {//状态模式  状态条件过多，把if判断去掉
	public abstract void dosomething(Person person);
}
