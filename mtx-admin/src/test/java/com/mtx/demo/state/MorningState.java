package com.mtx.demo.state;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MorningState extends State{
	@Override
	public void dosomething(Person person) {
		if (person.getHour()==7){
			System.out.println("早餐");
		}else {
			person.setState(new LaunchState());
			person.dosomething();
		}

	}
}
