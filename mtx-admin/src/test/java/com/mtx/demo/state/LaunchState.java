package com.mtx.demo.state;

/**
 * Created by Administrator on 2018/4/25.
 */
public class LaunchState extends State{
	@Override
	public void dosomething(Person person) {
		if (person.getHour()==12){
			System.out.println("午餐");
		}

	}
}
