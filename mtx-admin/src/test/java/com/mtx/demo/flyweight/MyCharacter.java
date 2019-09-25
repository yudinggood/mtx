package com.mtx.demo.flyweight;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MyCharacter {
	private char mychar;

	public MyCharacter(char mychar) {
		this.mychar = mychar;
	}

	public void  display(){
		System.out.println(mychar);
	}
}
