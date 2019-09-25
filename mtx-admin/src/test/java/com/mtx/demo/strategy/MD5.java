package com.mtx.demo.strategy;

/**
 * Created by Administrator on 2018/4/25.
 */
public class MD5 implements Strategy{
	@Override
	public void secret() {
		System.out.println("执行MD5加密");
	}
}
